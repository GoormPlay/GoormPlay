package com.goormplay.uiservice.ui.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.uiservice.ui.dto.event.ContentPlayEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/ui/play")
@RequiredArgsConstructor
@Slf4j
public class ContentPlayEventController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/{contentId}")
    public ResponseEntity<Map<String, String>> trackPlayEvent(@PathVariable String contentId, @RequestBody ContentPlayEventDto request, Authentication authentication) {
        try {
        @SuppressWarnings("unchecked")
        Map<String, String> principal = (Map<String, String>) authentication.getPrincipal();
        String userId = principal.get("memberId");
        request.setUserId(userId);
        publishEvent(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyMap());
        }
        return ResponseEntity.ok(Collections.singletonMap("message", "event tracked"));
    }



    private void publishEvent(@RequestBody ContentPlayEventDto dto){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String eventJson = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("raw-video-action-events", eventJson);
            log.info("Sent video event: {}", eventJson);
        }catch (JsonProcessingException e) {
            log.error("Failed to serialize video event", e);
        }

    }

}
