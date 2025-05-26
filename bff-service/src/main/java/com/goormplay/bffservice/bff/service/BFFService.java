package com.goormplay.bffservice.bff.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.bffservice.bff.Entity.OutboxEvent;
import com.goormplay.bffservice.bff.exception.Kafka.KafkaException;
import com.goormplay.bffservice.bff.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.goormplay.bffservice.bff.exception.Kafka.KafkaExceptionType.USER_JOIN_FAIL;

@Service
@Slf4j
@RequiredArgsConstructor
public class BFFService {
    private  final KafkaTemplate<String, String> kafkaTemplate;
    private  final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OutboxRepository outboxRepository;
    private final String USER_JOIN_LOGS_TOPIC = "user-join-logs";
    @Transactional
    public void join(String username, String password, String gender, int age) {
        String HashedPassword = bCryptPasswordEncoder.encode(password);

        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", LocalDateTime.now().toString());
        logData.put("username", username);
        logData.put("password", HashedPassword);
        logData.put("gender", gender);
        logData.put("age", age);

        try {
            String json = objectMapper.writeValueAsString(logData);
            outboxRepository.save(OutboxEvent.builder()
                    .id(UUID.randomUUID().toString())
                    .aggregateType("User")
                    .aggregateId(username)
                    .eventType("UserJoinEvent")
                    .payload(json)
                    .timestamp(LocalDateTime.now())
                    .processed(false)
                    .build());
        } catch (JsonProcessingException e) {
            log.error("Json 직렬화 실패  : {}.", username, e);
        }

    }
}
