package com.goormplay.authservice.auth.client;

import com.goormplay.authservice.Security.FeignHeaderConfig;
import com.goormplay.authservice.auth.dto.Member.MemberDto;
import com.goormplay.authservice.auth.dto.Member.MemberSignInDto;
import com.goormplay.authservice.auth.dto.SignInRequestDto;
import com.goormplay.authservice.auth.dto.SignUpRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "member-service" , configuration = FeignHeaderConfig.class)
public interface MemberClient {
    @PostMapping("/api/member/client")//회원가입
    void signUpMember(@RequestBody SignUpRequestDto dto);

    @DeleteMapping("/api/member/client/{username}")
    void deleteMember(@PathVariable("username")String username);
}
