package com.goormplay.memberservice.member.service;

import com.goormplay.memberservice.member.dto.SignUpRequestDto;

public interface MemberService {

    void joinMember(SignUpRequestDto dto);

    void deleteMember(String username);
}
