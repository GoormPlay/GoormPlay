package com.goormplay.uiservice.ui.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="interactions")
public class InteractionEntity {

    @Id
    private String id;

    private String userId;
    private String contentId;

    private boolean liked;
    private LocalDateTime updatedAt;
}
