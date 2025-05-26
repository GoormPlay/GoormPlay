package com.goormplay.authservice.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthRegistrationConsumer {

    private final ObjectMapper objectMapper; // JSON 파싱을 위함

    // @KafkaListener 어노테이션으로 토픽과 컨슈머 그룹을 지정합니다.
    @KafkaListener(topics = "user-registration-events", groupId = "auth-registration-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenUserRegistrationEvents(
            String message, // Kafka 메시지의 값 (payload)
            @Header(KafkaHeaders.RECEIVED_KEY) String key, // Kafka 메시지의 키
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition, // 메시지가 수신된 파티션 번호
            @Header(KafkaHeaders.OFFSET) long offset // 메시지의 오프셋
    ) {
        log.info("Kafka 메시지 수신 - 토픽: user-registration-events, 파티션: {}, 오프셋: {}, 키: {}", partition, offset, key);

        try {
            // 메시지(JSON 문자열)를 JsonNode로 파싱
            JsonNode eventJson = objectMapper.readTree(message);

            // 이벤트 페이로드에서 필요한 정보 추출 (예시)
            String username = eventJson.has("username") ? eventJson.get("username").asText() : "N/A";
            String email = eventJson.has("email") ? eventJson.get("email").asText() : "N/A";
            String timestamp = eventJson.has("timestamp") ? eventJson.get("timestamp").asText() : "N/A";
            String aggregateId = key; // Kafka key를 aggregateId로 사용 (Producer에서 그렇게 보냈으므로)

            log.info("회원 가입 이벤트 처리 시작 - 사용자: {}, 이메일: {}, 발생 시각: {}", username, email, timestamp);

            // -------------------------------------------------------------
            // 실제 비즈니스 로직이 들어가는 부분입니다.
            // 예:
            // 1. userRepository.existsByUsername(username)으로 중복 회원 확인
            // 2. new UserEntity(...) 객체 생성 및 userEntityRepository.save(...)
            // 3. 계좌 서비스로 계좌 생성 요청 (만약 계좌 서비스가 별도이고 이벤트를 받지 않는다면)
            // 4. 이벤트를 멱등성 있게 처리하기 위한 로직 (이미 처리된 이벤트인지 확인)
            // -------------------------------------------------------------

            log.info("회원 가입 이벤트 처리 완료 - 사용자: {}", username);

        } catch (Exception e) {
            log.error("회원 가입 이벤트 처리 중 오류 발생 (메시지: {}): {}", message, e.getMessage(), e);
            // 오류 처리:
            // - 예외를 던지면 Kafka 컨슈머는 해당 메시지를 다시 처리하려고 시도할 수 있습니다.
            // - 또는, 데드 레터 큐(DLQ)로 메시지를 전송하는 로직을 추가할 수 있습니다.
            // - 특정 비즈니스 예외는 로깅 후 무시할 수도 있습니다 (예: 이미 존재하는 사용자).
        }
    }
}
