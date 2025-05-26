package com.goormplay.reviewservice.review.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateReviewEventDto {
    private String userId;
    private String contentId;
    private String timestamp;
    private String review;
    private String eventType;
    private String page;

}
