package com.goormplay.contentservice.content.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewDTO {
    @Nullable
    private String id;
    private String userId;
    private String username;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
