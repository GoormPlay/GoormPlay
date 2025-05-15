package com.goormplay.adadminservice.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdResponseDTO {
    private String id;
    private String adSnId;
    private String type;
    private String title;
    private String thumbnailUrl;
    private String link;  // embedUrl을 link로 매핑
    private int impressions;
    private int clicks;
    private double cpc;  // 클릭당 비용
    private Long balance;  // 남은 예산
}
