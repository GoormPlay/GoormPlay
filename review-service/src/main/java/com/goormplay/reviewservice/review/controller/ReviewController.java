package com.goormplay.reviewservice.review.controller;

import com.goormplay.reviewservice.review.dto.CreateReviewRequest;
import com.goormplay.reviewservice.review.dto.ReviewResponse;
import com.goormplay.reviewservice.review.dto.UpdateReviewRequest;
import com.goormplay.reviewservice.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

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
}
