package com.goormplay.indexingservice.dto.raw;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawLikeToggleEventDto {
    private String userId;
    private String contentId;
    private boolean liked;
    private String timestamp;
}
