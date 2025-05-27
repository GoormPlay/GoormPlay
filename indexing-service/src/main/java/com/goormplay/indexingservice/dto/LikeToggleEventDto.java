package com.goormplay.indexingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeToggleEventDto {
    private String userId;
    private String contentId;
    private boolean liked;
    private String timestamp;
    private String eventType;
    private String page;
}
