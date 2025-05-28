package com.goormplay.indexingservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.indexingservice.dto.ContentPlayEventDto;
import com.goormplay.indexingservice.dto.RatingEventDto;
import com.goormplay.indexingservice.dto.VideoEventDto;
import com.goormplay.indexingservice.dto.raw.RawContentPlayEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentPlayEventConsumer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @KafkaListener(topics = "raw-video-action-events", groupId = "indexing-group2")
    public void consume(String message) {
        try {
            RawContentPlayEventDto eventDto = objectMapper.readValue(message, RawContentPlayEventDto.class);
            log.info("Received event: {}", eventDto);
            ContentPlayEventDto dto = ContentPlayEventDto.builder()
                    .userId(eventDto.getUserId())
                    .contentId(eventDto.getContentId())
                    .timestamp(eventDto.getTimestamp())
                    .eventType(eventDto.getEventType())
                    .page("content_play")
                    .build();
            publish(dto);
        }catch (Exception e) {
            log.error("Failed to process message while consuming video event: {}", message, e);
        }
    }

    private void publish(ContentPlayEventDto dto){
        try {
            String json = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("content-user-events", json);
            log.info("✅ Published processed playing content event: {}", json);
        } catch (JsonProcessingException e) {
            log.error("❌ Failed to serialize processed playing content event", e);
        }
    }
}
