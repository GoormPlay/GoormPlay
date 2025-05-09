package com.goormplay.reviewservice.review.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "reviews")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private String id;

    @NotBlank(message = "Content ID cannot be blank")
    private String contentId; // 콘텐츠 ID

    @NotBlank(message = "User ID cannot be blank")
    private String userId;    // 작성자 ID

    @NotBlank(message = "Review text cannot be blank")
    @Size(max = 500, message = "Review text cannot exceed 500 characters")
    private String text;      // 리뷰 텍스트

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating must be at most 5")
    private BigDecimal rating; // 별점

    private LocalDateTime timestamp; // 작성 시간

    // 생성자에서 timestamp 초기화
    public Review(String id, String contentId, String userId, String text, BigDecimal rating) {
        this.id = id;
        this.contentId = contentId;
        this.userId = userId;
        this.text = text;
        this.rating = rating;
        this.timestamp = LocalDateTime.now();
    }
}
