package com.goormplay.subscribeservice.subscribe.controller;

import com.goormplay.subscribeservice.subscribe.dto.SubScribe.SubScribeStatusDto;
import com.goormplay.subscribeservice.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subscribe")
public class SubscribeController {

    private final SubscribeService subscribeService;


    @PostMapping("/client/{memberId}")
    void joinSubscribe(@PathVariable("memberId") String memberId) {
        log.info("Subscribe Controller :  회원가입 시작 ");
        subscribeService.joinSubscribe(memberId);
    }

    @DeleteMapping("/client/{memberId}")
    void deleteSubscribe(@PathVariable("memberId") String memberId) {
        log.info("Subscribe Controller : 회원가입 보상 트랜잭션 구독 삭제 시작 ");
        subscribeService.deleteSubscribe(memberId);
    }


    @GetMapping("/client/{subscriptionId}/status")
    SubScribeStatusDto getSubScribeStatus(@PathVariable("subscriptionId") String memberId) {
        log.info("Subscribe Controller : 멤버 구독 정보 조회 시작 ");
        return subscribeService.findMemberProfile(memberId);
    }


    @PatchMapping("/status")
    public void patchSubscriptionStatus(Authentication authentication) {
        log.info("Subscribe Controller : 멤버 구독 상태 변경 시작");
        @SuppressWarnings("unchecked")
        Map<String, String> principal = (Map<String, String>) authentication.getPrincipal();
        String memberId = principal.get("memberId");
        subscribeService.changeSubscribeStatus(memberId);
    }

}
