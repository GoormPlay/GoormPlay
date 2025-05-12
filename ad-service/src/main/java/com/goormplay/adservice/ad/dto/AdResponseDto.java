package com.goormplay.adservice.ad.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder  // Builder 패턴 사용
public class AdResponseDto {
    private String id;
    private String title;
    private String adSnId;
    private String contentId;
    private String type;
    private String thumbnailUrl;
    private String embedUrl;
}