package com.goormplay.authservice.auth.client;

import com.goormplay.authservice.Security.FeignHeaderConfig;
import com.goormplay.authservice.auth.dto.SignUpRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-service" , configuration = FeignHeaderConfig.class)
public interface MemberClient {
    @PostMapping("/api/member/client")//회원가입
    Long signUpMember(@RequestBody SignUpRequestDto dto);

    @DeleteMapping("/api/member/client/{username}")
    void deleteMember(@PathVariable("username")String username);
}
