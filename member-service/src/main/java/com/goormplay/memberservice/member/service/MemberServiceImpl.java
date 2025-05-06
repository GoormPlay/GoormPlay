package com.goormplay.memberservice.member.service;

import com.goormplay.memberservice.member.dto.Member.MemberDto;
import com.goormplay.memberservice.member.dto.SignUpRequestDto;
import com.goormplay.memberservice.member.entity.Member;
import com.goormplay.memberservice.member.exception.Member.MemberException;
import com.goormplay.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.goormplay.memberservice.member.exception.Member.MemberExceptionType.ALREADY_EXIST_MEMBER;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public void joinMember(SignUpRequestDto dto) {
        log.info("Member Service 회원가입 시작");


        String username = dto.getUsername();
        String gender = dto.getGender();
        int age = dto.getAge();
        log.info("username, gender, age :" + username+", "+gender+", "+age);
        memberRepository.save(Member.builder().
                username(username).
                gender(gender).
                isCancelScheduled(false).
                isSubcribe(false).
                age(age).
                build());
    }

    public void deleteMember(String username) {
        log.info("유저 삭제 시작");
        memberRepository.findByUsername(username)
                .ifPresent(memberRepository::delete);
    }
}
