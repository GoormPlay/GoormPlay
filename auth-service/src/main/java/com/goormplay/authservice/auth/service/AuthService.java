package com.goormplay.authservice.auth.service;

import com.goormplay.authservice.auth.dto.Member.MemberDto;
import com.goormplay.authservice.auth.dto.SignInRequestDto;


public interface AuthService {

    String signIn(SignInRequestDto dto);
    String createJwt(MemberDto memberDto);
    String tokenRefresh();
    void logout();
}
