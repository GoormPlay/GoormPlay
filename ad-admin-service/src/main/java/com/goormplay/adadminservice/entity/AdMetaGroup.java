package com.goormplay.adadminservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.goormplay.adadminservice.tool.AdStatusDeserializer;
import com.goormplay.adadminservice.tool.AdStatusSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "ad_meta_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdMetaGroup {
    // 광고 메타 및 A/B 집계용 도큐먼트

    @Id
    private String id;
    private String advertiserId;
    private String adInstanceId; // UUID-xxxx
    private String adSnId; // AD001
    private String title;
    private Long budget;

    private AdStatus status;

    @Field("ads")
    private List<AdVariant> ads;

    private LocalDateTime createdAt;

    public void updateStatus(int statusCode) {
        this.status = AdStatus.fromStatusCode(statusCode);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdVariant{
        private String type; // A or B
        private String thumbnail;
        private String embedUrl;
        private int impressions;
        private int clicks;

        public void incrementImpressions(){
            impressions++;
        }
        public void incrementClicks(){
            clicks++;
        }
    }


}
