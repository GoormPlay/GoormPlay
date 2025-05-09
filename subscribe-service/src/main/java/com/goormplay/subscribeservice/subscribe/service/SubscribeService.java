package com.goormplay.subscribeservice.subscribe.service;

import com.goormplay.subscribeservice.subscribe.dto.SubScribe.SubScribeStatusDto;

public interface SubscribeService {
    SubScribeStatusDto findMemberProfile(Long subscriptionId);
    void changeSubscribeStatus(Long subscriptionId);

}
