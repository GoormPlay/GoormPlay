package com.goormplay.memberservice.member.service;

import com.goormplay.memberservice.member.dto.Member.MemberProfileDto;
import com.goormplay.memberservice.member.dto.SignUpRequestDto;

public interface MemberService {

    String joinMember(SignUpRequestDto dto);
    void deleteMember(String username);

    MemberProfileDto findMemberProfile(String MemberId);
}
