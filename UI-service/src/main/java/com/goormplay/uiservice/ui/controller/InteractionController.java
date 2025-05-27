package com.goormplay.uiservice.ui.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.uiservice.ui.dto.InteractionRequestDto;
import com.goormplay.uiservice.ui.dto.LikedContentDto;
import com.goormplay.uiservice.ui.dto.event.LikeToggleEventDto;
import com.goormplay.uiservice.ui.service.InteractionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ui")
public class InteractionController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final InteractionService interactionService;
    private final ObjectMapper objectMapper;

    @GetMapping("/content/{contentId}/liked/{userId}")
    public boolean isContentLikedByUser(
            @PathVariable String contentId, @PathVariable String userId) {
        return interactionService.isContentLikedByUser(contentId, userId);
    }

    @PostMapping("/like")
    public void likeContent(@RequestBody InteractionRequestDto requestDto, Authentication authentication) {
        log.info("Interaction Controller :  좋아요 상태 변경 시작");
        @SuppressWarnings("unchecked")
        Map<String, String> principal = (Map<String, String>) authentication.getPrincipal();
        String userId = principal.get("memberId");
        boolean liked = interactionService.toggleLike(
                userId,
                requestDto.getContentId()
        );
        LikeToggleEventDto event = LikeToggleEventDto.builder()
                .userId(userId)
                .contentId(requestDto.getContentId())
                .liked(liked)
                .timestamp(requestDto.getTimestamp())
                .build();
        publish(event);
    }
    // contentIds로 content 조회
   @GetMapping("/content/{userId}/liked")
   public List<String>  getLikedContentsId(@PathVariable String userId) {
        return interactionService.getLikedContentIds(userId);
   }

    private void publish(LikeToggleEventDto eventDto) {
        try {
            String json = objectMapper.writeValueAsString(eventDto);
            kafkaTemplate.send("raw-like-click-events",json);
            log.info("Like Click event sent: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Kafka serialization failed", e);
        }
    }

    
}
