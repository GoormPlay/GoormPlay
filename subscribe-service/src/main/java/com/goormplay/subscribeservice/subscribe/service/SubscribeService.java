package com.goormplay.subscribeservice.subscribe.service;

import com.goormplay.subscribeservice.subscribe.dto.SubScribe.SubScribeStatusDto;

public interface SubscribeService {
    SubScribeStatusDto findMemberProfile(String memberId);
    void changeSubscribeStatus(String memberId);
    void signUpSubscribe(String memberId);

//    void deleteSubscribe(String memberId);
}
