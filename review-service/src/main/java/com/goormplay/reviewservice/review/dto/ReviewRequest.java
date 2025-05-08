package com.goormplay.reviewservice.review.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private String userId;   // 작성자 ID
    private String text;     // 리뷰 텍스트
    private double rating;   // 별점
}