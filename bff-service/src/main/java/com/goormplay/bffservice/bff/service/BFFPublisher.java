package com.goormplay.bffservice.bff.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.bffservice.bff.Entity.OutboxEvent;
import com.goormplay.bffservice.bff.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class BFFPublisher {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final OutboxEventProcessor outboxEventProcessor;

    private static final String USER_REGISTRATION_TOPIC = "user-registration-events";
    @Scheduled(fixedDelay = 5000)
    public void publishPendingOutboxEvents() {
        log.debug("Outbox 이벤트 발행 스케줄러 실행");

        List<OutboxEvent> pendingEvents = outboxRepository.findByProcessedFalse();

        if (pendingEvents.isEmpty()) {
            log.debug("발행할 Outbox 이벤트 없음.");
            return;
        }

        log.info("발행할 Outbox 이벤트 {}개 발견.", pendingEvents.size());

        for (OutboxEvent event : pendingEvents) {
            try {

                kafkaTemplate.send(USER_REGISTRATION_TOPIC, event.getAggregateId(), event.getPayload())
                        .whenComplete((result, ex) -> { // CompletableFuture의 콜백
                    if (ex == null) {

                        outboxEventProcessor.markEventAsProcessed(event.getId());
                        log.info("Outbox 이벤트 '{}' 발행 : 토픽={}, 파티션={}, 오프셋={}",
                                event.getId(), result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("Outbox 이벤트 '{}' 발행 실패: {}", event.getId(), ex.getMessage());

                    }
                });

            } catch (Exception e) {
                log.error("Outbox 이벤트 발행 중 예외 발생 (ID: {}): {}", event.getId(), e.getMessage());
            }
        }
    }
}
