package com.goormplay.bffservice.bff.client;

import com.goormplay.bffservice.Security.FeignHeaderConfig;
import com.goormplay.bffservice.bff.dto.Member.MemberProfileDto;
import com.goormplay.bffservice.bff.dto.SignUpRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "subscribe-service" , configuration = FeignHeaderConfig.class)
public interface SubscribeClient {
    @PostMapping("/client/{memberId}")
    void signUpSubscribe(@PathVariable("memberId") String memberId);
    @GetMapping("/api/subscribe/client/profile/{memberId}")
    MemberProfileDto getMemberProfile(@PathVariable("memberId") String memberId);

}
