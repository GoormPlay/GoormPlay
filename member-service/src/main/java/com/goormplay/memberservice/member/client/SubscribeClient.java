package com.goormplay.memberservice.member.client;

import com.goormplay.memberservice.Security.FeignHeaderConfig;
import com.goormplay.memberservice.member.dto.Member.SubScribeStatusDto;
import com.goormplay.memberservice.member.dto.SignUpRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "subscribe-service" , configuration = FeignHeaderConfig.class)
public interface SubscribeClient {


    @GetMapping("/api/subcribe/client/{memberId}")
    void joinSubscribe(@PathVariable("memberId") String memberId);
    @DeleteMapping("/api/subcribe/client/{memberId}")
    void deleteSubscribe(@PathVariable("memberId") String memberId);

    @GetMapping("/api/subscribe/client/{memberId}/status")
    SubScribeStatusDto getSubScribeStatus(@PathVariable("memberId") String memberId);


}
