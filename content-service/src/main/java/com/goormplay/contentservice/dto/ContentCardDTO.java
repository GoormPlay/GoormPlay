package com.goormplay.contentservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentCardDTO {
    private String contentId;
    private String title;
    private String contentType;
    private String[] genre;
    private String thumbnailUrl;
}
