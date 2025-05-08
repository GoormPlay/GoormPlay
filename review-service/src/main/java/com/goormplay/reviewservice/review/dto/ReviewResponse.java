package com.goormplay.reviewservice.review.dto;

import com.goormplay.reviewservice.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewResponse {
    private String userId;
    private String text;
    private double rating;
    private LocalDateTime timestamp;

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
            review.getUserId(),
            review.getText(),
            review.getRating(),
            review.getTimestamp()
        );
    }
}
