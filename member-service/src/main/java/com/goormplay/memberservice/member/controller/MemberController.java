package com.goormplay.memberservice.member.controller;

import com.goormplay.memberservice.member.dto.Member.MemberDto;
import com.goormplay.memberservice.member.dto.Member.MemberSignInDto;
import com.goormplay.memberservice.member.dto.ResponseDto;
import com.goormplay.memberservice.member.dto.SignInRequestDto;
import com.goormplay.memberservice.member.dto.SignUpRequestDto;
import com.goormplay.memberservice.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/member/client")
    public ResponseEntity<ResponseDto> singUpMember(SignUpRequestDto dto) {
        log.info("Member Service 회원가입 시작 ");
        memberService.joinMember(dto);
        return new ResponseEntity<>(new ResponseDto("회원가입",null), HttpStatus.OK);
    }
}
