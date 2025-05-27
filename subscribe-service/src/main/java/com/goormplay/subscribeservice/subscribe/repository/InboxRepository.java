package com.goormplay.subscribeservice.subscribe.repository;

import com.goormplay.subscribeservice.subscribe.entity.InboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InboxRepository extends JpaRepository<InboxEvent,String> {
    @Query(value = "SELECT * FROM inbox_event WHERE processed = false ORDER BY created_at ASC LIMIT :limit", nativeQuery = true)
    List<InboxEvent> findPendingEventsWithLimit(@Param("limit") int limit);
}
