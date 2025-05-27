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
import java.util.Optional;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final KafkaTemplate<String, String> kafkaTemplate;
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
            request.setUserId(principal.get("memberId"));
            request.setUsername(principal.get("username"));
            request.setContentId(contentId);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        reviewService.createReview(contentId, request);

        publishCreateReviewEvent(request);
        publishRatingEvent(request);

        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateReview(@RequestBody UpdateReviewRequest request, Authentication authentication) {
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
    public ResponseEntity<Void> deleteReview(@RequestParam String reviewId, Authentication authentication) {
        @SuppressWarnings("unchecked")
        Map<String, String> principal = (Map<String, String>) authentication.getPrincipal();
        String userId = principal.get("memberId");
        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    public void publishCreateReviewEvent(@RequestBody CreateReviewRequest event){
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("raw-review-write-events", json);
            log.info("Sent review event: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Kafka send failed", e);
        }
    }

    public void publishRatingEvent(@RequestBody CreateReviewRequest event){
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("raw-rating-submit-events", json);
            log.info("Sent rating event: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Kafka send failed", e);
        }
    }
}
