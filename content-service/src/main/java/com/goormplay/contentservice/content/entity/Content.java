package com.goormplay.contentservice.content.entity;

import com.goormplay.contentservice.content.dto.ContentCardDTO;
import com.goormplay.contentservice.content.dto.ContentDetailDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "contents")
public class Content {
    @Id
    private String id;

    @Field("title")
    private String title;

    @Indexed
    private String kind;

    private String[] genre;
    private int year;
    private String KMRB;
    private String[] cast;
    private String[] director;
    private String[] provider;
    private String videoId;
    private String thumbnail;
    private String synopsis;

    @Builder.Default
    private int likesCount = 0;
    @Builder.Default
    private int commentsCount = 0;

    private LocalDate releaseDate; // 출시일


    @CreatedDate
    private LocalDate createdAt;
    @LastModifiedDate
    private LocalDate updatedAt;



    // 컨텐츠 Card DT
    public ContentCardDTO contentToContentCardDto(Content content){
        return ContentCardDTO.builder()
                .id(content.getId())
                .title(content.getTitle())
                .kind(content.getKind())
                .genre(content.getGenre())
                .thumbnail(content.getThumbnail())
                .videoId(content.getVideoId())
                .build();

    }
    // 컨텐츠 Detail DTO
    public ContentDetailDTO contentToContentDetailDto(Content content){
        return ContentDetailDTO.builder()
                .id(content.getId())
                .title(content.getTitle())
                .kind(content.getKind())
                .genre(content.getGenre())
                .year(content.getYear())
                .KMRB(content.getKMRB())
                .cast(content.getCast())
                .director(content.getDirector())
                .videoId(content.getVideoId())
                .releaseDate(content.getReleaseDate())
                .thumbnail(content.getThumbnail())
                .synopsis(content.getSynopsis())
                .provider(content.getProvider())
                .build();
    }



}
