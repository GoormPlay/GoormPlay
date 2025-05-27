package com.goormplay.indexingservice.dto.raw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawContentPlayEventDto {
    private String userId;
    private String contentId;
    private String timestamp;
    private String eventType;      // e.g., "play", "pause", "end", "exit"
    private double watchProgress;    // current playback position in seconds
}
