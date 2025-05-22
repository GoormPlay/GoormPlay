package com.goormplay.contentservice.dto;

import com.goormplay.contentservice.entity.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentCardDTO {
    private String id;
    private String title;
    private String kind;
    private String[] genre;
    private String videoId;
    private String thumbnail;


}
