package com.goormplay.uiservice.ui.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="user_interaction")
public class InteractionEntity {

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("content_id")
    private String contentId;
    
    private Interactions interactions;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static class Interactions {
        private boolean liked;

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }
    }

    
}
