package com.goormplay.useractiontestservice.domain;

import com.mongodb.lang.Nullable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "video_events")
@Data
public class VideoEvent {
    @Id
    private String id;
    private String videoId;
    private String eventType;
    private LocalDateTime timestamp;
    private double currentTime;
    @Nullable
    private String userId;  // 필요한 경우
}
