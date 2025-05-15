package com.goormplay.adadminservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdMetaGroupDTO {
    private String adInstanceId;
    private String advertiserId;
    private String adSnId;
    private String title;
    private Long budget;
    private List<AdVariantDTO> ads;
    private Integer status; // 1- ONGOING 2- ENDED
    private LocalDateTime createdAt;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AdVariantDTO {
        private String type;
        private String thumbnail;
        private String embedUrl;
        private int impression;
        private int clicks;
    }
}
