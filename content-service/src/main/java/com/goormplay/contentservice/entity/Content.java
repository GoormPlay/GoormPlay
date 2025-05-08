package com.goormplay.contentservice.entity;

import com.goormplay.contentservice.dto.ContentCardDTO;
import com.goormplay.contentservice.dto.ContentDTO;
import com.goormplay.contentservice.dto.ContentDetailDTO;
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
    private String contentType;

    private String[] genre;
    private int year;
    private String KMRB;
    private String[] cast;
    private String embedUrl;
    private String thumbnailUrl;

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
                .contentId(content.getId())
                .title(content.getTitle())
                .contentType(content.getContentType())
                .genre(content.getGenre())
                .thumbnailUrl(content.getThumbnailUrl())
                .embedUrl(content.getEmbedUrl())
                .build();
    }
    // 컨텐츠 Detail DTO
    public ContentDetailDTO contentToContentDetailDto(Content content){
        return ContentDetailDTO.builder()
                .id(content.getId())
                .title(content.getTitle())
                .contentType(content.getContentType())
                .genre(content.getGenre())
                .year(content.getYear())
                .KMRB(content.getKMRB())
                .cast(content.getCast())
                .embedUrl(content.getEmbedUrl())
                .releaseDate(content.getReleaseDate())
                .thumbnailUrl(content.getThumbnailUrl())
                .build();
    }



}
