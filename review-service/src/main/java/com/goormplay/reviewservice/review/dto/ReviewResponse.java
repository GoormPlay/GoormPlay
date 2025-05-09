package com.goormplay.reviewservice.review.dto;

import com.goormplay.reviewservice.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class ReviewResponse {
    private String userId;
    private String text;
    private BigDecimal rating; // BigDecimal로 변경
    private LocalDateTime timestamp;

    public static ReviewResponse of(Review review) {
        Objects.requireNonNull(review, "Review cannot be null");
        return ReviewResponse.builder()
            .userId(review.getUserId())
            .text(review.getText())
            .rating(review.getRating()) // BigDecimal 그대로 사용
            .timestamp(review.getTimestamp())
            .build();
    }
}
