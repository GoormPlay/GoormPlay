package com.goormplay.reviewservice.review.controller;

import com.goormplay.reviewservice.review.dto.ReviewRequest;
import com.goormplay.reviewservice.review.dto.ReviewResponse;
import com.goormplay.reviewservice.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{contentId}")
    public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable String contentId) {
        List<ReviewResponse> reviews = reviewService.getReviews(contentId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{contentId}")
    public ResponseEntity<Void> createReview(@PathVariable String contentId, @RequestBody ReviewRequest request) {
        reviewService.createReview(contentId, request);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    @PatchMapping("/{contentId}")
    public ResponseEntity<Void> updateReview(@PathVariable String contentId, @RequestBody ReviewRequest request) {
        reviewService.updateReview(contentId, request);
        return ResponseEntity.ok().build(); // 200 OK
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String contentId, @RequestBody Map<String, String> payload) {
        reviewService.deleteReview(contentId, payload.get("userId"));
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
