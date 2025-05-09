package com.goormplay.subscribeservice.subscribe.service;

import com.goormplay.subscribeservice.subscribe.dto.SubScribe.SubScribeStatusDto;
import com.goormplay.subscribeservice.subscribe.entity.Subscribe;
import com.goormplay.subscribeservice.subscribe.exception.Subscribe.SubscribeException;
import com.goormplay.subscribeservice.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.goormplay.subscribeservice.subscribe.exception.Subscribe.SubscribeExceptionType.NOT_FOUND_MEMBER;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscribesServiceImpl implements SubscribeService{

    private final SubscribeRepository subscribeRepository;

    @Override
    public SubScribeStatusDto findMemberProfile(Long subscriptionId) {
        Subscribe subscribe = findSubscribeById(subscriptionId);
        return SubScribeStatusDto.builder().
                subscription_start_date(subscribe.getSubscription_start_date()).
                subscription_end_date(subscribe.getSubscription_end_date()).
                is_cancel_scheduled(subscribe.getIs_cancel_scheduled()).
                build();
    }

    @Override
    @Transactional
    public void changeSubscribeStatus(Long subscriptionId) {
        Subscribe subscribe = findSubscribeById(subscriptionId);
        boolean is_cancel_scheduled = subscribe.getIs_cancel_scheduled();
        if(is_cancel_scheduled){
            subscribe.setIs_cancel_scheduled(false);
        }else{
            subscribe.setIs_cancel_scheduled(true);
        }
    }

    public Subscribe findSubscribeById(Long id){
        return subscribeRepository.findById(id).orElseThrow(()->new SubscribeException(NOT_FOUND_MEMBER));
    }
 }
