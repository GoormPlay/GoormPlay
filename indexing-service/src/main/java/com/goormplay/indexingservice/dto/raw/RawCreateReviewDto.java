package com.goormplay.indexingservice.dto.raw;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RawCreateReviewDto {
    private String userId;   // 작성자 ID
    private String username;
    private String contentId;
    private String comment;     // 리뷰 텍스트
    private double rating;   // 별점
}