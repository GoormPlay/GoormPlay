package com.goormplay.reviewservice.review.service;

import com.goormplay.reviewservice.review.domain.Review;
import com.goormplay.reviewservice.review.dto.ReviewRequest;
import com.goormplay.reviewservice.review.dto.ReviewResponse;
import com.goormplay.reviewservice.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewResponse> getReviews(String contentId) {
        return reviewRepository.findByContentId(contentId).stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());
    }

    public void createReview(String contentId, ReviewRequest request) {
        Review review = new Review(null, contentId, request.getUserId(), request.getText(), request.getRating(), LocalDateTime.now());
        reviewRepository.save(review);
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