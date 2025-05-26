package com.goormplay.reviewservice.review.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateReviewRequest {
    @Nullable
    @Size(max = 50, message = "User ID cannot exceed 50 characters")
    private String userId;   // 작성자 ID

    @Nullable
    private String username;

    @NotBlank(message = "Review text cannot be blank")
    @Size(min = 10, max = 500, message = "Review text must be between 1 and 500 characters")
    private String comment;     // 리뷰 텍스트

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must be at most 5")
    private double rating;   // 별점
}