package com.goormplay.indexingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingEventDto {
    private String userId;
    private String contentId;
    private String timestamp;
    private String rating;
    private String eventType;
    private String page;
}
