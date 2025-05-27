package com.goormplay.indexingservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.indexingservice.dto.ClickEventLogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContentClickEventConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "content-click-events", groupId = "indexing-group2")
    public void consume(String message) {
        try {
            ClickEventLogDto event = objectMapper.readValue(message, ClickEventLogDto.class);
            log.info("✅ Received click event: {}", event);

            // TODO: 이곳에 DB 저장 또는 인덱싱 로직 추가

        } catch (Exception e) {
            log.error("❌ Failed to process click event: {}", message, e);
        }
    }
}
