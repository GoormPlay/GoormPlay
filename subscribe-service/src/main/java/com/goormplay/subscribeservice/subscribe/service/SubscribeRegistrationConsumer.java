package com.goormplay.subscribeservice.subscribe.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.subscribeservice.subscribe.entity.Subscribe;
import com.goormplay.subscribeservice.subscribe.exception.Subscribe.SubscribeException;
import com.goormplay.subscribeservice.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

import static com.goormplay.subscribeservice.subscribe.exception.Subscribe.SubscribeExceptionType.EVENT_PROCESSING_FAILED;
import static com.goormplay.subscribeservice.subscribe.exception.Subscribe.SubscribeExceptionType.JSON_PARSING_EX;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscribeRegistrationConsumer {

    private final ObjectMapper objectMapper;
    private final SubscribeRepository subscribeRepository;

    @KafkaListener(topics = "user-registration-events", groupId = "Subscribe-registration-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenUserRegistrationEvents(
            String message, // Kafka 메시지의 값 (payload)
            @Header(KafkaHeaders.RECEIVED_KEY) String key, // Kafka 메시지의 키
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition, // 메시지가 수신된 파티션 번호
            @Header(KafkaHeaders.OFFSET) long offset // 메시지의 오프셋
    ) {
        log.info("Kafka 메시지 수신 - 토픽: user-registration-events, 오프셋: {}, 키: {}", offset, key);

        try {
            JsonNode eventJson = objectMapper.readTree(message);


            String username = eventJson.has("username") ? eventJson.get("username").asText() : null;

            if (username == null || key == null) {
                log.warn("필수 정보 누락된 회원 가입 이벤트 수신: 메시지={}", message);
                // 이 경우 이 메시지는 처리할 수 없으므로, 더 이상 재처리하지 않고 넘어가거나 DLQ로 보냅니다.
                return;
            }

            // 멱등성 처리: 이미 해당 memberId에 대한 구독 정보가 있는지 확인
            if (subscribeRepository.existsByMemberId(username)) { // memberId로 username
                log.info("사용자 '{}'의 구독 정보가 이미 존재합니다. 오프셋: {}", username, offset);
                return;
            }

            // 초기 구독 정보 생성
            LocalDate subscriptionStartDate = LocalDate.now();
            LocalDate subscriptionEndDate = subscriptionStartDate.plusMonths(1);

            Subscribe newSubscribe = Subscribe.builder()
                    .id(UUID.randomUUID().toString())
                    .subscriptionStartDate(subscriptionStartDate)
                    .subscriptionEndDate(subscriptionEndDate)
                    .isCancelScheduled(false)
                    .memberId(username) // 이 구독의 주인이 되는 memberId
                    .build();

            subscribeRepository.save(newSubscribe); // 구독 정보 DB 저장

            log.info("사용자 '{}'의 초기 구독 정보 생성 완료. 구독 ID: {}", username, newSubscribe.getId());

        } catch (DataIntegrityViolationException e) {
            // DB 유니크 제약 조건 위반
            log.warn("사용자 '{}'의 구독 정보 생성 실패: DB 제약 조건 위반, 중복. 오프셋: {}", key, offset, e.getMessage());
            // 이 경우 역시 중복 이벤트로 간주하고 무시합니다.
        } catch (JsonProcessingException e) {
            log.error("Kafka 메시지 파싱 오류 (메시지: {}): {}", message, e.getMessage(), e);
            // 메시지 구조가 잘못되었을 경우, 재처리해도 성공할 가능성이 낮으므로 DLQ
            throw new SubscribeException(JSON_PARSING_EX); // 컨슈머 재시도 유도
        } catch (Exception e) {
            log.error("회원 가입 이벤트 처리 중 예상치 못한 오류 발생 (메시지: {}): {}", message, e.getMessage(), e);
            // 재처리 필요
            throw new SubscribeException(EVENT_PROCESSING_FAILED); // 컨슈머 재시도 유도
        }
    }
}
