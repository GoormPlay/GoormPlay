package com.goormplay.adservice.ad.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data  // Lombok을 사용하여 getter/setter 자동 생성
@Document(collection = "advertisements")  // MongoDB 컬렉션 매핑
@Builder
public class Advertisement {
    @Id  // MongoDB의 _id 필드
    private String id;

    private String title;
    private String adSnId;
    private String contentId;
    private String type;  // "A-type" 또는 "B-type"
    private String thumbnailUrl;
    private String embedUrl;
}