package com.goormplay.indexingservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.indexingservice.dto.CreateReviewEventDto;
import com.goormplay.indexingservice.dto.raw.RawCreateReviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewEventConsumer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "raw-review-write-events", groupId = "indexing-group2")
    public void handleReviewWriteEvent(String message) {
        try {
            RawCreateReviewDto dto = objectMapper.readValue(message, RawCreateReviewDto.class);
            log.info("Received review event: {}", dto);
            CreateReviewEventDto logDto = CreateReviewEventDto.builder()
                    .userId(dto.getUserId())
                    .contentId(dto.getContentId())
                    .review(dto.getComment())
                    .timestamp(LocalDateTime.now().toString())
                    .eventType("review_write")
                    .page("content_detail")
                    .build();
            publish(logDto);
        } catch (Exception e) {
            log.error("Failed to deserialize review-write event: {}", message, e);
        }
    }

    private void publish(CreateReviewEventDto dto){
        try {
            String json = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("content-user-events", json);
            log.info("✅ Published processed creating review event: {}", json);
        } catch (JsonProcessingException e) {
            log.error("❌ Failed to serialize processed review event", e);
        }
    }
}
