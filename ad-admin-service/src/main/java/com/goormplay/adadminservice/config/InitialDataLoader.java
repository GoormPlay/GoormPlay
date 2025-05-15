package com.goormplay.adadminservice.config;

import com.goormplay.adadminservice.dto.AdvertiserAccountDTO;
import com.goormplay.adadminservice.dto.RechargeRequestDTO;
import com.goormplay.adadminservice.service.budget.AdBudgetService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialDataLoader {
    private final AdBudgetService adBudgetService;
    private static final String DEFAULT_ADVERTISER_ID = "ADV_001";
    private static final Long INITIAL_BALANCE = 1000000L; // 100만원

    @PostConstruct
    public void init() {
        try {
            // 이미 존재하는지 확인
            try {
                adBudgetService.checkBalance(DEFAULT_ADVERTISER_ID);
                log.info("Default advertiser account already exists: {}", DEFAULT_ADVERTISER_ID);
            } catch (IllegalArgumentException e) {
                // 계정이 없는 경우 새로 생성하고 초기 잔액 충전
                RechargeRequestDTO rechargeRequest = RechargeRequestDTO.builder()
                        .advertiserId(DEFAULT_ADVERTISER_ID)
                        .amount(INITIAL_BALANCE)
                        .build();

                AdvertiserAccountDTO account = adBudgetService.rechargeBalance(rechargeRequest);
                log.info("Created default advertiser account: {} with initial balance: {}",
                        DEFAULT_ADVERTISER_ID, INITIAL_BALANCE);
            }
        } catch (Exception e) {
            log.error("Error initializing default advertiser account", e);
        }
    }
}
