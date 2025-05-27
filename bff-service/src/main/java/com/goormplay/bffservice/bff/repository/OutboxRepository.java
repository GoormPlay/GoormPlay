package com.goormplay.bffservice.bff.repository;

import com.goormplay.bffservice.bff.Entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxEvent, String> {
    List<OutboxEvent> findByProcessedFalse();


    @Query(value = "SELECT * FROM outbox_event WHERE processed = false ORDER BY created_at ASC LIMIT :limit", nativeQuery = true)
    List<OutboxEvent> findPendingEventsWithLimit(@Param("limit") int limit);

}
