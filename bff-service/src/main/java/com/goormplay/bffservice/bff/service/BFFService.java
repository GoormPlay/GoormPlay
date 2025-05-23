package com.goormplay.bffservice.bff.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class BFFService {
    private  final KafkaTemplate<String, String> kafkaTemplate;
    private  final ObjectMapper objectMapper;

    public void join(String userId, String action, String description, Map<String, Object> details) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", LocalDateTime.now().toString());
        logData.put("userId", userId);
        logData.put("action", action);
        logData.put("description", description);
        logData.put("details", details); // 추가 정보 (예: 상품 ID, 검색어 등)
        String TOPIC_NAME = "user-action-logs";

        try {
            String json = objectMapper.writeValueAsString(logData);
            kafkaTemplate.send(TOPIC_NAME, userId, json); // Key는 userId로 하여 파티션 분배
            System.out.println("Sent user action log to Kafka: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 또는 로그로 남기기
        }

    }
}
