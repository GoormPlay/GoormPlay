package com.goormplay.adadminservice.repository;

import com.goormplay.adadminservice.entity.AdTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdTransactionLogRepository extends JpaRepository<AdTransactionLog, Long> {
    List<AdTransactionLog> findByAdSnId(String adSnId);
}
