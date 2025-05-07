package com.goormplay.memberservice.member.service;

import com.goormplay.memberservice.member.dto.SignUpRequestDto;
import com.goormplay.memberservice.member.entity.Gender;
import com.goormplay.memberservice.member.entity.Member;
import com.goormplay.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Long joinMember(SignUpRequestDto dto) {
        log.info("Member Service 회원가입 시작");


        Member member =  memberRepository.save(Member.builder().
                username(dto.getUsername()).
                gender(dto.getGender()).
                age(dto.getAge()).
                build());

        return member.getId();
    }

    public void deleteMember(String username) {
        log.info("유저 삭제 시작");
        memberRepository.findByUsername(username)
                .ifPresent(memberRepository::delete);
    }
}
