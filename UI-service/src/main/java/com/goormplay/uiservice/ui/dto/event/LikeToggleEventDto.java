package com.goormplay.uiservice.ui.dto.event;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeToggleEventDto {
    @Nullable
    private String userId; // 백엔드에서 붙임
    private String contentId;
    private boolean liked;
    private String timestamp;
}
