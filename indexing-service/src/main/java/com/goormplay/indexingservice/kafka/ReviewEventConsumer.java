package com.goormplay.indexingservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.indexingservice.dto.CreateReviewEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReviewEventConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "review-write-events", groupId = "indexing-group2")
    public void handleReviewWriteEvent(String message) {
        try {
            CreateReviewEventDto dto = objectMapper.readValue(message, CreateReviewEventDto.class);
            log.info("Received review event: {}", dto);
        } catch (Exception e) {
            log.error("Failed to deserialize review-write event: {}", message, e);
        }
    }
}
