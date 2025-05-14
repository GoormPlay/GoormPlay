package com.goormplay.useractiontestservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.useractiontestservice.domain.VideoEvent;
import com.goormplay.useractiontestservice.dto.VideoEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Slf4j
public class VideoEventController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    // kafka topic
    private static final String TOPIC = "video-action-events";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/video")
    public ResponseEntity<Map<String, String>> trackEvent(@RequestBody VideoEventDto event){
        log.info("Received video event: videoId={}, eventType={}, currentTime={}, timestamp={}",
                event.getVideoId(), event.getEventType(), event.getCurrentTime(), event.getTimestamp());
        publishEvent(event);
        return ResponseEntity.ok(Collections.singletonMap("message", "event tracked"));
    }

    @PostMapping
    public void publishEvent(@RequestBody VideoEventDto event){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, eventJson);
            log.info("Sent video event: {}", eventJson);
        }catch (JsonProcessingException e) {
            log.error("Failed to serialize video event", e);
        }

    }

}
