package com.goormplay.reviewservice.review.dto;

import com.goormplay.reviewservice.review.entity.Review;
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
    private String id;
    private String userId;
    private String username;
    private String comment;
    private double rating; //double로 변경: 리뷰 별점을 정밀하게 계산할 필요는 없을 것 같음..
    private LocalDateTime createdAt;

    public static ReviewResponse of(Review review) {
        Objects.requireNonNull(review, "Review cannot be null");
        return ReviewResponse.builder()
            .userId(review.getUserId())
                .username(review.getUsername())
            .comment(review.getComment())
            .rating(review.getRating()) // BigDecimal 그대로 사용
            .createdAt(review.getCreatedAt())
            .build();
    }


}
