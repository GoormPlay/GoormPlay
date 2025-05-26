package com.goormplay.indexingservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.indexingservice.dto.VideoEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReviewEventConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @KafkaListener(topics = "review-create-events", groupId = "indexing-group2")
    public void consume(String message) {
        try {
            VideoEventDto eventDto = objectMapper.readValue(message, VideoEventDto.class);
            log.info("Received event: {}", eventDto);
        }catch (Exception e) {
            log.error("Failed to process message while consuming video event: {}", message, e);
        }
    }
}
