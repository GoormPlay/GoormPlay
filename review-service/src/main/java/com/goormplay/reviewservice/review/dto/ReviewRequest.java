package com.goormplay.reviewservice.review.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ReviewRequest {
    @NotNull(message = "User ID cannot be null")
    @Size(max = 50, message = "User ID cannot exceed 50 characters")
    private String userId;   // 작성자 ID

    @NotBlank(message = "Review text cannot be blank")
    @Size(min = 10, max = 500, message = "Review text must be between 1 and 500 characters")
    private String text;     // 리뷰 텍스트

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must be at most 5")
    private BigDecimal rating;   // 별점
}