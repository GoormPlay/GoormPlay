package com.goormplay.uiservice.ui.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.uiservice.ui.dto.ClickEventLogDto;
import com.goormplay.uiservice.ui.dto.ContentClickEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ui/click")
@RequiredArgsConstructor
@Slf4j
public class ContentClickEventController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/content")
    public ResponseEntity<Void> handleClickEvent(@RequestBody ContentClickEventDto request, Authentication authentication) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> principal = (Map<String, String>) authentication.getPrincipal();
            String userId = principal.get("memberId");
            request.setUserId(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (request.isTrending() || request.isLatest()) {
            sendToKafka(ClickEventLogDto.builder()
                    .userId(request.getUserId())
                    .contentId(request.getContentId())
                    .timestamp(request.getTimestamp())
                    .eventType("content_click")
                    .contentCategory(request.getGenre())  // ✅ 장르 그대로 전달
                    .page("content_detail")
                    .build());
        }
        if (request.isRecommended()) {
            sendToKafka(ClickEventLogDto.builder()
                    .userId(request.getUserId())
                    .contentId(request.getContentId())
                    .timestamp(request.getTimestamp())
                    .eventType("content_recom_click")
                    .page("content_detail")
                    .build());
        }


        return ResponseEntity.ok().build();
    }

    private void sendToKafka(ClickEventLogDto logDto) {
        try {
            String json = objectMapper.writeValueAsString(logDto);
            kafkaTemplate.send("content-click-events", json);
            log.info("Click event sent: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Kafka serialization failed", e);
        }
    }
}
