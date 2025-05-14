package com.goormplay.bffservice.bff.client;

import com.goormplay.bffservice.Security.FeignHeaderConfig;
import com.goormplay.bffservice.bff.dto.Member.MemberProfileDto;
import com.goormplay.bffservice.bff.dto.SignUpRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-service" , configuration = FeignHeaderConfig.class)
public interface MemberClient {
    @PostMapping("/api/member/client")
    String signUpMember(@RequestBody SignUpRequestDto dto);

    @GetMapping("/api/member/client/profile/{memberId}")
    MemberProfileDto getMemberProfile(@PathVariable("memberId") String memberId);
}

