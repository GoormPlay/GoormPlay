package com.goormplay.memberservice.member.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentCardDto {
    private String id;
    private String title;
    private String kind;
    private String[] genre;
    private String thumbnail;
    private String videoId;
}
