package com.goormplay.memberservice.member.client;

import com.goormplay.memberservice.Security.FeignHeaderConfig;
import com.goormplay.memberservice.member.dto.Member.SubScribeStatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "subscribe-service" , configuration = FeignHeaderConfig.class)
public interface SubscribeClient {


    @GetMapping("/subscribe/client/{memberId}")
    void joinSubscribe(@PathVariable("memberId") String memberId);
    @DeleteMapping("/subscribe/client/{memberId}")
    void deleteSubscribe(@PathVariable("memberId") String memberId);

    @GetMapping("/subscribe/client/{memberId}/status")
    SubScribeStatusDto getSubScribeStatus(@PathVariable("memberId") String memberId);


}
