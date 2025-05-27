package com.goormplay.indexingservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.indexingservice.dto.RatingEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RatingEventConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "rating-submit-events", groupId = "indexing-group2")
    public void handleRatingEvent(String message) {
        try {
            RatingEventDto dto = objectMapper.readValue(message, RatingEventDto.class);
            log.info("Received rating event: {}", dto);
        } catch (Exception e) {
            log.error("Failed to deserialize rating-submit event: {}", message, e);
        }
    }
}