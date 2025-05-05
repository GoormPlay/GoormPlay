package com.goormplay.authservice.auth.client;

import com.goormplay.authservice.auth.dto.Member.MemberDto;
import com.goormplay.authservice.auth.dto.Member.MemberSignInDto;
import com.goormplay.authservice.auth.dto.SignInRequestDto;
import com.goormplay.authservice.auth.dto.SignUpRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "member-service")
public interface MemberClient {
    @GetMapping("/member/client/{memberId}")
    MemberDto getMember(@PathVariable("memberId") String memberId);

    @PostMapping("/member/client/validate")//유효한 회원인지 확인
    MemberSignInDto checkMemberBeforeSignIn(SignInRequestDto dto); 
    
    @PostMapping("/member/client")//회원가입
    void singUpMember(SignUpRequestDto dto);

}
