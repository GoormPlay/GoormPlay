package com.goormplay.contentservice.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
