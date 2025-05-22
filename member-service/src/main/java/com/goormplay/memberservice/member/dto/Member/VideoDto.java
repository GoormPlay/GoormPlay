package com.goormplay.memberservice.member.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private String id;
    private String title;
    private String kind;
    private String[] genre;
    private String videoId;
    private String thumbnail;
    private String synopsis;
    private Boolean trending;
    private Boolean latest;
    private Boolean recommended;

    // 상세 정보 필드
    private int year;
    private String KMRB;
    private String[] cast;
    private String[] provider;
    private String[] director;
    private LocalDate releaseDate;
}
