package com.goormplay.uiservice.ui.dto.event;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentPlayEventDto {
    @Nullable
    private String userId; // 백엔드에서 붙임
    private String contentId;
    private String timestamp;
    private String eventType;      // e.g., "play", "pause", "end", "exit"
    private double watchProgress;    // current playback position in seconds
}
