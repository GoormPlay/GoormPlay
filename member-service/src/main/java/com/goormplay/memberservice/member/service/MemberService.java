package com.goormplay.memberservice.member.service;

import com.goormplay.memberservice.member.dto.Member.MemberDto;
import com.goormplay.memberservice.member.dto.SignUpRequestDto;
import com.goormplay.memberservice.member.entity.Member;

public interface MemberService {

    void joinMember(SignUpRequestDto dto);
}
