package com.goormplay.subscribeservice.subscribe.repository;

import com.goormplay.subscribeservice.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Optional<Subscribe> findById(Long id);
}
