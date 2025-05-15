package com.goormplay.adadminservice.repository;

import com.goormplay.adadminservice.entity.AdvertiserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AdvertiserAccountRepository extends JpaRepository<AdvertiserAccount, Long> {
    Optional<AdvertiserAccount> findByAdvertiserId(String advertiserId);
}
