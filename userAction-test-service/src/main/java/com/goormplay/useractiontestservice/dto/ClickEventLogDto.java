package com.goormplay.useractiontestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickEventLogDto {
    private String userId;
    private String contentId;
    private String timestamp;
    private String eventType;
    private List<String> contentCategory;
    private String page;
}
