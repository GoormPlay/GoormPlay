package com.goormplay.subscribeservice.subscribe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "subscribe", indexes = {
        @Index(name = "idx_subscription_end_date", columnList = "subscriptionEndDate"),
        @Index(name = "idx_username", columnList = "username")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscribe {

    @Id
    @Column(name = "id", updatable = false, length = 36)
    @NotBlank(message = "구독 ID는 비어 있을 수 없음.")
    @Size(max = 36, message = "구독 ID는 36자를 초과할 수 없음.")
    private String id;

    @Column(name = "subscription_start_date", nullable = false)
    @NotNull(message = "구독 시작일은 null일 수 없습니다.")
    @FutureOrPresent(message = "구독 시작일은 오늘 또는 미래 날짜")
    private LocalDate subscriptionStartDate;

    @Column(name = "subscription_end_date", nullable = false)
    @NotNull(message = "구독 종료일은 null일 수 없음.")
    @FutureOrPresent(message = "구독 종료일은 오늘 또는 미래 날짜.")
    private LocalDate subscriptionEndDate;

    @Column(name = "is_cancel_scheduled", nullable = false)
    @NotNull(message = "구독 취소 예정 상태는 null일 수 없음")
    private Boolean isCancelScheduled;

    @Column(name = "username", nullable = false, unique = true, length = 255)
    @NotBlank(message = "사용자 이름은 비어 있을 수 없음")
    @Size(max = 255, message = "사용자 이름은 255자를 초과할 수 없습니다.")
    private String username;


    public void toggleCancelScheduled() {
        this.isCancelScheduled = !this.isCancelScheduled;
    }

    public void setCancelScheduled(boolean scheduled) {
        this.isCancelScheduled = scheduled;
    }

    public boolean isSubscribed() {
        LocalDate today = LocalDate.now();
        // 구독 시작일이 오늘이거나 지났고, 구독 종료일이 오늘이거나 지나지 않았다면 유효한 구독
        return !this.subscriptionStartDate.isAfter(today) // 시작일 <= 오늘
                && this.subscriptionEndDate.isAfter(today); // 종료일 > 오늘
    }
}
