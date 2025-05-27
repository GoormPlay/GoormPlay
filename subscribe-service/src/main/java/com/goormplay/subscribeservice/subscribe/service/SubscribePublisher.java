package com.goormplay.subscribeservice.subscribe.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.subscribeservice.subscribe.entity.InboxEvent;
import com.goormplay.subscribeservice.subscribe.repository.InboxRepository;
import com.goormplay.subscribeservice.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscribePublisher {

    private  final InboxRepository inboxRepository;


    private final InboxEventProcessor outboxEventProcessor;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RedissonClient redissonClient;


    private static final String SUBSCRIBE_REGISTRATION_TOPIC = "subscribe-registration-events";
    private static final String OUTBOX_LOCK_KEY = "outbox:publisher:lock";
    @Scheduled(fixedDelay = 5000) // 5초마다 실행
    public void publishPendingOutboxEvents() {
        log.info("Outbox 이벤트 발행 스케줄러");

        // 1. Redisson 분산 락 획득 시도
        RLock lock = redissonClient.getLock(OUTBOX_LOCK_KEY);
        try {

            boolean isLocked = lock.tryLock(10, 30, TimeUnit.SECONDS);

            if (isLocked) {
                log.info("분산 락 획득, Outbox 이벤트 발행.");

                List<InboxEvent> pendingEvents = inboxRepository.findPendingEventsWithLimit(10);

                if (pendingEvents.isEmpty()) {
                    log.debug("Outbox 이벤트 없음.");
                    return;
                }

                log.info("Outbox 이벤트 {}개.", pendingEvents.size());

                for (InboxEvent event : pendingEvents) {
                    try {
                        kafkaTemplate.send(SUBSCRIBE_REGISTRATION_TOPIC, event.getId(), event.getPayload())
                                .whenComplete((result, ex) -> {
                                    if (ex == null) {
                                        // Kafka 발행 성공 시 DB의 상태 업데이트 (트랜잭션 적용)
                                        outboxEventProcessor.markEventAsProcessed(event.getId());
                                        log.info("Outbox 이벤트 '{}' 발행: 토픽={}, 파티션={}, 오프셋={}",
                                                event.getId(), result.getRecordMetadata().topic(),
                                                result.getRecordMetadata().partition(),
                                                result.getRecordMetadata().offset());
                                    } else {
                                        log.error("Outbox 이벤트 '{}' 발행 실패: {}", event.getId(), ex.getMessage());
                                        // 발행 실패 시 processed=false 상태 유지 , 다음 스케줄러 실행 시 재시도

                                    }
                                });

                    } catch (Exception e) {
                        log.error("Outbox 이벤트 발행 중 예외 발생 (ID: {}): {}", event.getId(), e.getMessage());
                    }
                }
            } else {
                log.debug("분산 락 획득 실패. 다른 인스턴스에서 처리 중.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 상태 복원
            log.error("락 획득 대기 중 인터럽트 발생: {}", e.getMessage());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("락 해제.");
            }
        }
    }
}
