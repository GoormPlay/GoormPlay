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
public class ClickEventLogDto {
    @Nullable
    private String userId;
    private String contentId;
    private String timestamp;
    private String eventType;             // "content_click" 또는 "content_recom_click"
    private String page;                  // ex: "content_detail"
    private List<String> contentCategory; // ← 여기 genre 그대로 담음
}
