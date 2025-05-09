package com.goormplay.subscribeservice.subscribe.repository;

import com.goormplay.subscribeservice.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, String> {
    Optional<Subscribe> findByMemberId(String memberid);

    @Modifying//벌크 연산
    @Query("update Subscribe s set s.subscriptionEndDate = :nextMonth " +
            "where s.subscriptionEndDate = :today and s.isCancelScheduled = false")
    void extendSubscriptions(@Param("nextMonth") LocalDate nextMonth,
                            @Param("today") LocalDate today);
}
