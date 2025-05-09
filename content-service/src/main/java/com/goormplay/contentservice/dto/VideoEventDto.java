package com.goormplay.contentservice.dto;

import lombok.Data;

@Data
public class VideoEventDto {
    private String videoId;
    private String eventType;      // e.g., "play", "pause", "end", "exit"
    private String timestamp;      // ISO-8601 string, e.g., "2025-05-09T10:00:00Z"
    private double currentTime;    // current playback position in seconds
}
