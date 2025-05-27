package com.goormplay.subscribeservice.subscribe.service;

import com.goormplay.subscribeservice.subscribe.repository.InboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InboxEventProcessor { // 별도의 서비스로 분리

    private final InboxRepository inboxRepository;

    // 이 메서드는 OutboxEventPublisher에서 호출될 것이며, 이제 별도의 트랜잭션을 가집니다.
    @Transactional
    public void markEventAsProcessed(String eventId) {
        inboxRepository.findById(eventId).ifPresent(event -> {
            event.setProcessed(true);
            inboxRepository.save(event);
            log.debug("Outbox 이벤트 '{}' processed 상태 true로 업데이트 완료.", eventId);
        });
    }
}
