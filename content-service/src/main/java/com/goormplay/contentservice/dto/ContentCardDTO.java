package com.goormplay.contentservice.dto;

import com.goormplay.contentservice.entity.Content;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContentCardDTO {
    private String id;
    private String title;
    private String kind;
    private String[] genre;
    private String thumbnail;
    private String videoId;

}
