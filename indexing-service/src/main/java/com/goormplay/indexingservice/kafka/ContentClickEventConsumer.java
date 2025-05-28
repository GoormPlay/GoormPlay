package com.goormplay.indexingservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.indexingservice.dto.ClickEventLogDto;
import com.goormplay.indexingservice.dto.raw.RawClickEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentClickEventConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "raw-content-click-events", groupId = "indexing-group2")
    public void consume(String message) {
        try {
            RawClickEventDto original = objectMapper.readValue(message, RawClickEventDto.class);
            log.info("📩 Received raw click event: {}", original);

            // 가공 로직
            if (original.isRecommended()) {
                ClickEventLogDto logDto = ClickEventLogDto.builder()
                        .userId(original.getUserId())
                        .contentId(original.getContentId())
                        .timestamp(original.getTimestamp())
                        .eventType("content_recom_click")
                        .contentCategory(original.getGenre())
                        .page("content_detail")
                        .build();
                publish(logDto);
            }

            if (original.isTrending() || original.isLatest()) {
                ClickEventLogDto logDto = ClickEventLogDto.builder()
                        .userId(original.getUserId())
                        .contentId(original.getContentId())
                        .timestamp(original.getTimestamp())
                        .eventType("content_click")
                        .page("content_detail")
                        .contentCategory(original.getGenre())
                        .build();
                publish(logDto);
            }

        } catch (Exception e) {
            log.error("❌ Failed to process raw click event", e);
        }
    }

    private void publish(ClickEventLogDto logDto) {
        try {
            String json = objectMapper.writeValueAsString(logDto);
            kafkaTemplate.send("content-user-events", json);
            log.info("✅ Published processed click event: {}", json);
        } catch (Exception e) {
            log.error("❌ Failed to publish click event", e);
        }
    }
}
