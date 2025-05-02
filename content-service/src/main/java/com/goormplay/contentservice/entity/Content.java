package com.goormplay.contentservice.entity;

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
    private String thumbnailUrl;
    private int likesCount;
    private int commentsCount;
    private LocalDate releaseDate; // 출시일

    @CreatedDate
    private LocalDate createdAt;
    @LastModifiedDate
    private LocalDate updatedAt;
}
