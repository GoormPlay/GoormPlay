package com.goormplay.indexingservice.dto.raw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawClickEventDto {
    private String userId;
    private String contentId;
    private String timestamp;
    private List<String> genre;
    private boolean trending;
    private boolean latest;
    private boolean recommended;
}
