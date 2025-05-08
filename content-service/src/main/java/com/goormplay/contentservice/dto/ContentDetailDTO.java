package com.goormplay.contentservice.repository.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContentDetailDTO {
    private String id;
    private String title;
    private String contentType;
    private String[] genre;
    private int year;
    private String KMRB;
    private String[] cast;
    private String embedUrl;
    private String thumbnailUrl;
    private int commentsCount;
    private LocalDate releaseDate;
}
