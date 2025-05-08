package com.goormplay.reviewservice.review.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private String id;
    private String contentId; // 콘텐츠 ID
    private String userId;    // 작성자 ID
    private String text;      // 리뷰 텍스트
    private double rating;    // 별점
    private LocalDateTime timestamp; // 작성 시간
}
