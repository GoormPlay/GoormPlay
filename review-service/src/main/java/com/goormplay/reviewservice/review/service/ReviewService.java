package com.goormplay.reviewservice.review.service;

import com.goormplay.reviewservice.review.dto.ReviewRequest;
import com.goormplay.reviewservice.review.dto.ReviewResponse;
import com.goormplay.reviewservice.review.entity.Review;
import com.goormplay.reviewservice.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewResponse> getReviews(String contentId) {
        return reviewRepository.findByContentId(contentId).stream()
                .map(ReviewResponse::of)
                .collect(Collectors.toList());
    }

    public void createReview(String contentId, ReviewRequest request) {

        reviewRepository.save(Review.builder().id(UUID.randomUUID().toString())
                .contentId(contentId)
                .userId(request.getUserId())
                .text(request.getText())
                .rating(request.getRating())
                .build());
    }

    public void updateReview(String contentId, ReviewRequest request) {
        Review review = reviewRepository.findByContentIdAndUserId(contentId, request.getUserId())
                .orElseThrow();
        review.setText(request.getText());
        review.setRating(request.getRating());
        reviewRepository.save(review);
    }

    public void deleteReview(String contentId, String userId) {
        reviewRepository.deleteByContentIdAndUserId(contentId, userId);
    }
}