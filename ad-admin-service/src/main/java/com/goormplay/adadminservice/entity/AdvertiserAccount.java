package com.goormplay.adadminservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "advertiser_account")
public class AdvertiserAccount {
    // 광고 예산 및 결제 관리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String advertiserId;
    private Long totalBudget;
    private Long currentBalance;
    private LocalDateTime createdAt;

    public void deductBalance(Long amount) {
        if(currentBalance >= amount){
            currentBalance -= amount;
        } else  {
            throw new IllegalArgumentException("잔액 부족");
        }
    }

    public void recharge(Long amount) {
        this.currentBalance += amount;
        this.totalBudget += amount;  // totalBudget도 함께 증가
    }
}
