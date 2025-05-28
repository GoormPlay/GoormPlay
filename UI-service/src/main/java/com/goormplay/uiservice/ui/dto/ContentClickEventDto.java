package com.goormplay.uiservice.ui.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentClickEventDto {
    private String contentId;
    private List<String> genre;
    private String timestamp;
    private boolean trending;
    private boolean latest;
    private boolean recommended;

    @Nullable
    private String userId; // 서버에서 설정
}
