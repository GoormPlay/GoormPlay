package com.goormplay.indexingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewEventDto {
    private String userId;
    private String contentId;
    private String timestamp;
    private String review;
    private String eventType;
    private String page;

}
