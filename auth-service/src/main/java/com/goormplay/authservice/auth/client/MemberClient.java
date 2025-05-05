package com.goormplay.authservice.auth.client;

import com.goormplay.authservice.auth.dto.Member.MemberDto;
import com.goormplay.authservice.auth.dto.Member.MemberSignInDto;
import com.goormplay.authservice.auth.dto.SignInRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "member-service")
public interface MemberClient {
    @GetMapping("/member/client/{memberId}")
    MemberDto getMember(@PathVariable("memberId") String memberId);

    @PostMapping("/member/client")
    MemberSignInDto checkMemberBeforeSignIn(SignInRequestDto dto);

}
