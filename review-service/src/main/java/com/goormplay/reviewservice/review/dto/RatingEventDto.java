package com.goormplay.reviewservice.review.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingEventDto {
    private String userId;
    private String contentId;
    private String timestamp;
    private String rating;
    private String eventType;
    private String page;
}
