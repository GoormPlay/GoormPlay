package com.goormplay.useractiontestservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.useractiontestservice.dto.ClickEventLogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestContentClickEventConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "content-user-events", groupId = "indexing-group2")
    public void consume(String message) {
        try {
            com.goormplay.useractiontestservice.dto.ClickEventLogDto original = objectMapper.readValue(message, ClickEventLogDto.class);
            log.info("📩 Received test click event: {}", original);



        } catch (Exception e) {
            log.error("❌ Failed to process raw click event", e);
        }
    }


}
