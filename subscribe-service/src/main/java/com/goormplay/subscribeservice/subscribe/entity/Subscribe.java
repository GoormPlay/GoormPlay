package com.goormplay.subscribeservice.subscribe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "subscribe", indexes = {
        @Index(name = "idx_subscription_end_date", columnList = "subscriptionEndDate")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscribe {

    @Id
    @Column(name = "id", updatable = false)
    private String id;

    private LocalDate subscriptionStartDate;

    private LocalDate subscriptionEndDate;

    @Column(nullable = false)
    private Boolean isCancelScheduled;

    private String memberId;

    public void toggleCancelScheduled() {
        this.isCancelScheduled = !this.isCancelScheduled;
    }

    public void setCancelScheduled() {
        this.isCancelScheduled = false;
    }

    public boolean isSubscribed() {
        LocalDate today = LocalDate.now();
        return this.subscriptionStartDate.isBefore(today)
                && this.subscriptionEndDate.isAfter(today);
    }

}
