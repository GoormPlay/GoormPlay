package com.goormplay.reviewservice.review.controller;

import com.goormplay.reviewservice.review.dto.ReviewRequest;
import com.goormplay.reviewservice.review.dto.ReviewResponse;
import com.goormplay.reviewservice.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{contentId}")
    public List<ReviewResponse> getReviews(@PathVariable String contentId) {
        return reviewService.getReviews(contentId);
    }

    @PostMapping("/{contentId}")
    public void createReview(@PathVariable String contentId, @RequestBody ReviewRequest request) {
        reviewService.createReview(contentId, request);
    }

    @PatchMapping("/{contentId}")
    public void updateReview(@PathVariable String contentId, @RequestBody ReviewRequest request) {
        reviewService.updateReview(contentId, request);
    }

    @DeleteMapping("/{contentId}")
    public void deleteReview(@PathVariable String contentId, @RequestBody Map<String, String> payload) {
        reviewService.deleteReview(contentId, payload.get("userId"));
    }
}
