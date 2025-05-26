package com.goormplay.bffservice.bff.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.bffservice.bff.Entity.OutboxEvent;
import com.goormplay.bffservice.bff.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class BFFPublisher {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final OutboxEventProcessor outboxEventProcessor;
    private final RedissonClient redissonClient;
    private static final String USER_REGISTRATION_TOPIC = "user-registration-events";
    private static final String OUTBOX_LOCK_KEY = "outbox:publisher:lock";
    @Scheduled(fixedDelay = 5000) // 5초마다 실행
    public void publishPendingOutboxEvents() {
        log.debug("Outbox 이벤트 발행 스케줄러");

        // 1. Redisson 분산 락 획득 시도
        RLock lock = redissonClient.getLock(OUTBOX_LOCK_KEY);
        try {

            boolean isLocked = lock.tryLock(10, 30, TimeUnit.SECONDS);

            if (isLocked) {
                log.info("분산 락 획득 성공. Outbox 이벤트 발행 시작.");

                List<OutboxEvent> pendingEvents = outboxRepository.findByProcessedFalse();

                if (pendingEvents.isEmpty()) {
                    log.debug("발행할 Outbox 이벤트 없음.");
                    return;
                }

                log.info("발행할 Outbox 이벤트 {}개 발견.", pendingEvents.size());

                for (OutboxEvent event : pendingEvents) {
                    try {
                       kafkaTemplate.send(USER_REGISTRATION_TOPIC, event.getAggregateId(), event.getPayload())
                        .whenComplete((result, ex) -> {
                            if (ex == null) {
                                // Kafka 발행 성공 시 DB의 상태 업데이트 (트랜잭션 적용)
                                outboxEventProcessor.markEventAsProcessed(event.getId());
                                log.info("Outbox 이벤트 '{}' 발행 성공: 토픽={}, 파티션={}, 오프셋={}",
                                        event.getId(), result.getRecordMetadata().topic(),
                                        result.getRecordMetadata().partition(),
                                        result.getRecordMetadata().offset());
                            } else {
                                log.error("Outbox 이벤트 '{}' 발행 실패: {}", event.getId(), ex.getMessage());
                                // 발행 실패 시 processed=false 상태 유지 -> 다음 스케줄러 실행 시 재시도

                            }
                        });

                    } catch (Exception e) {
                        log.error("Outbox 이벤트 발행 중 예외 발생 (ID: {}): {}", event.getId(), e.getMessage());
                        // 이 예외는 KafkaTemplate.send()가 비동기 Future를 반환하기 전에 발생한 동기적 예외 (거의 없음)
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
                log.info("분산 락 해제.");
            }
        }
    }
}
