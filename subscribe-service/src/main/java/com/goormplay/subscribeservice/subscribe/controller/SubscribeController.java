package com.goormplay.subscribeservice.subscribe.controller;

import com.goormplay.subscribeservice.subscribe.dto.ResponseDto;
import com.goormplay.subscribeservice.subscribe.dto.SubScribe.SubScribeStatusDto;
import com.goormplay.subscribeservice.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/subscribe")
public class SubscribeController {

    private final SubscribeService subscribeService;




    
    @GetMapping("/client/{subscriptionId}/status")
    SubScribeStatusDto getSubScribeStatus(@PathVariable("subscriptionId") Long subscriptionId){
        return subscribeService.findMemberProfile(subscriptionId);
    }


    @PatchMapping("/status")
    public void patchSubscriptionStatus(Authentication authentication){
        log.info("Subscribe service - 구독 상태 변경 시작");
        String member_Id = authentication.getName();
        subscribeService.changeSubscribeStatus(Long.parseLong(authentication.getName()));
    }

}
