package com.goormplay.contentservice.content.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContentDetailDTO {
    private String id;
    private String title;
    private String kind;
    private String[] genre;
    private int year;
    private String KMRB;
    private String[] cast;
    private String[] provider;
    private String thumbnail;
    private String[] director;
    private String videoId;
    private LocalDate releaseDate;
    private String synopsis;
}
