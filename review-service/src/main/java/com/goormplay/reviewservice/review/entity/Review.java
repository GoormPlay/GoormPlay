package com.goormplay.reviewservice.review.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "reviews")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(name = "content_user_idx", def = "{'contentId': 1, 'userId': 1}")//복합인덱스의 첫번째 컬럼은 혼자써도 인덱스 사용가능
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

    @CreatedDate
    private LocalDateTime timestamp; // 작성 시간


}
