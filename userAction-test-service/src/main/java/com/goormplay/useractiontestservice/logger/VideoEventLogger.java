package com.goormplay.useractiontestservice.logger;

import com.goormplay.useractiontestservice.dto.VideoEventDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class VideoEventLogger {
    // 별도의 Logger 인스턴스를 생성하지 않고 @Slf4j의 log를 사용
    private String page = "video_play";

    public void logVideoEvent(VideoEventDto event) {
        String anonymousUserId = generateAnonymousUserId();
        String logMessage = formatLogMessage(anonymousUserId, event, page);
        log.info(logMessage); // 단순히 메시지만 기록
    }

    private String generateAnonymousUserId() {
        return "anonymous-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private String formatLogMessage(String userId, VideoEventDto event, String page) {
        return String.format("user_id=%s,video_id=%s,event_type=%s,timestamp=%s,page=%s",
                userId,
                event.getVideoId(),
                event.getEventType(),
                event.getTimestamp(),
                page);
    }
}
