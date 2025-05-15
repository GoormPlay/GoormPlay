package com.goormplay.adadminservice.service.budget;

import com.goormplay.adadminservice.dto.AdvertiserAccountDTO;
import com.goormplay.adadminservice.dto.RechargeRequestDTO;
import com.goormplay.adadminservice.entity.AdTransactionLog;
import com.goormplay.adadminservice.entity.AdvertiserAccount;
import com.goormplay.adadminservice.repository.AdTransactionLogRepository;
import com.goormplay.adadminservice.repository.AdvertiserAccountRepository;
import com.goormplay.adadminservice.tool.AdvertiserAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdBudgetService {
    private final AdvertiserAccountRepository advertiserAccountRepository;
    private final AdTransactionLogRepository  adTransactionLogRepository;
    private final AdvertiserAccountMapper accountMapper;
    public void deductCost(String advertiserId, String adSnId, String adInstanceId, String type, String userId, Long cost, String eventType) {
        AdvertiserAccount account = advertiserAccountRepository.findByAdvertiserId(advertiserId)
                .orElseThrow(() -> new IllegalArgumentException("Advertiser not found: " + advertiserId));

        account.deductBalance(cost); // 예산 차감
        advertiserAccountRepository.save(account); // 갱신
    }

    public AdvertiserAccountDTO getAdvertiserAccount(String advertiserId) {
        AdvertiserAccount account = advertiserAccountRepository.findByAdvertiserId(advertiserId)
                .orElseThrow(() -> new IllegalArgumentException("광고주 계정을 찾을 수 없습니다."));
        return accountMapper.toDto(account);
    }

    public AdvertiserAccountDTO rechargeBalance(RechargeRequestDTO request) {
        AdvertiserAccount account = advertiserAccountRepository.findByAdvertiserId(request.getAdvertiserId())
                .orElseGet(() -> createNewAdvertiserAccount(request.getAdvertiserId()));

        account.recharge(request.getAmount());
        account = advertiserAccountRepository.save(account);
        return accountMapper.toDto(account);
    }
    public AdvertiserAccountDTO checkBalance(String advertiserId) {
        AdvertiserAccount account = advertiserAccountRepository.findByAdvertiserId(advertiserId)
                .orElseThrow(() -> new IllegalArgumentException("광고주 계정을 찾을 수 없습니다."));
        return accountMapper.toDto(account);
    }

    private AdvertiserAccount createNewAdvertiserAccount(String advertiserId) {
        return AdvertiserAccount.builder()
                .advertiserId(advertiserId)
                .totalBudget(0L)
                .currentBalance(0L)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
