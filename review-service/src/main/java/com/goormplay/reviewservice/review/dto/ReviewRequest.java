package com.goormplay.reviewservice.review.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class ReviewRequest {
    @NotNull(message = "User ID cannot be null")
    private String userId;   // 작성자 ID

    @NotBlank(message = "Review text cannot be blank")
    @Size(max = 500, message = "Review text cannot exceed 500 characters")
    private String text;     // 리뷰 텍스트

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating must be at most 5")
    private double rating;   // 별점
}