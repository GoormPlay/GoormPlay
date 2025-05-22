package com.goormplay.useractiontestservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.useractiontestservice.domain.VideoEvent;
import com.goormplay.useractiontestservice.dto.VideoEventDto;
import com.goormplay.useractiontestservice.logger.VideoEventLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class VideoEventController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    // kafka topic
    private static final String TOPIC = "video-action-events";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final VideoEventLogger videoEventLogger = new VideoEventLogger();

    @PostMapping("/video")
    public ResponseEntity<Map<String, String>> trackEvent(@RequestBody VideoEventDto event){
        // 시스템 로그는 콘솔에만 출력됨
        log.debug("Received video event: videoId={}, eventType={}, currentTime={}, timestamp={}",
                event.getVideoId(), event.getEventType(), event.getCurrentTime(), event.getTimestamp());

        videoEventLogger.logVideoEvent(event);
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
