package com.goormplay.contentservice.repository.dto;

import com.goormplay.contentservice.entity.Content;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;

@Data
@Builder
public class ContentDTO {
    private String title;
    private String contentType;
    private String[] genre;
    private int year;
    private String KMRB;
    private String[] cast;
    private String embedUrl;
    private String thumbnailUrl;
    private LocalDate releaseDate;


}
