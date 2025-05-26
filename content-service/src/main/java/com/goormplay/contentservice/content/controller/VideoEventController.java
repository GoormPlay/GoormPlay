package com.goormplay.contentservice.content.controller;

import com.goormplay.contentservice.content.dto.VideoEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@Slf4j
public class VideoEventController {
    @PostMapping("/video-old")
    public ResponseEntity<Map<String, String>> trackEvent(@RequestBody VideoEventDto event){
        log.info("Received video event: videoId={}, eventType={}, currentTime={}, timestamp={}",
                event.getVideoId(), event.getEventType(), event.getCurrentTime(), event.getTimestamp());
        return ResponseEntity.ok(Collections.singletonMap("message", "event tracked"));
    }
}
