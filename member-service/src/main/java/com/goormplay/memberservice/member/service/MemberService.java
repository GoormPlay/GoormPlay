package com.goormplay.memberservice.member.service;

import com.goormplay.memberservice.member.dto.SignUpRequestDto;

public interface MemberService {

    Long joinMember(SignUpRequestDto dto);

    void deleteMember(String username);
}
