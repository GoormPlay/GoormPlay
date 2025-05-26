package com.goormplay.reviewservice.review.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.reviewservice.review.dto.*;
import com.goormplay.reviewservice.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    // kafka topic

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ReviewService reviewService;

    @GetMapping("/{contentId}/list")
    public List<ReviewResponse> getReviews(@PathVariable String contentId) {
        return reviewService.getReviews(contentId);

    }

    @PostMapping("/new")
    public ResponseEntity<Void> createReview(@RequestParam String contentId, @RequestBody CreateReviewRequest request, Authentication authentication) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> principal = (Map<String, String>) authentication.getPrincipal();
            String userId = principal.get("memberId");
            String userName = principal.get("username");
            request.setUserId(userId);
            request.setUsername(userName);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        reviewService.createReview(contentId, request);
        log.debug(request.toString());
        publishCreateReviewEvent(CreateReviewEventDto.builder()
                .userId(request.getUserId())
                .contentId(contentId)
                .timestamp(LocalDateTime.now().toString())
                .eventType("review_write")
                .page("content_detail")
                .build());
        publishRatingEvent(RatingEventDto.builder()
                .userId(request.getUserId())
                .contentId(contentId)
                .timestamp(LocalDateTime.now().toString())
                .eventType("rating_submit")
                .page("content_detail")
                .build());
        return ResponseEntity.status(201).build(); // 201 Created
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateReview(@RequestParam UpdateReviewRequest request, Authentication authentication) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> principal = (Map<String, String>) authentication.getPrincipal();
            String userId = principal.get("memberId");
            String userName = principal.get("username");
            request.setUserId(userId);
            request.setUsername(userName);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        reviewService.updateReview(request);
        return ResponseEntity.ok().build(); // 200 OK
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteReview(@RequestParam String contentId, Authentication authentication) {
        @SuppressWarnings("unchecked")
        Map<String, String> principal = (Map<String, String>) authentication.getPrincipal();
        String userId = principal.get("memberId");
        reviewService.deleteReview(contentId, userId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    public void publishCreateReviewEvent(@RequestBody CreateReviewEventDto event){
        try {
            String TOPIC = "review-write-events";
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, eventJson);
            log.info("Sent review write event: {}", eventJson);
        } catch (Exception e) {
            log.warn("Kafka unavailable, skipping event publish. Cause: {}", e.getMessage());
        }
    }

    public void publishRatingEvent(@RequestBody RatingEventDto event){
        try {
            String TOPIC = "rating-submit-events";
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, eventJson);
            log.info("Sent rating submit event: {}", eventJson);
        } catch (Exception e) {
            log.warn("Kafka unavailable, skipping rating publish. Cause: {}", e.getMessage());
        }
    }
}
