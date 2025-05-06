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


    @PostMapping("/client")
    public ResponseEntity<ResponseDto> singUpMember(@RequestBody SignUpRequestDto dto) {
        log.info("Member Service 회원가입 시작 ");
        memberService.joinMember(dto);
        return new ResponseEntity<>(new ResponseDto("회원가입",null), HttpStatus.OK);
    }

    @DeleteMapping("client/{username}")
    public ResponseEntity<ResponseDto> signUpMember(@PathVariable("username")String username){
        log.info("회원가입 보상 트랜잭션 유저 삭제 시작 ");
        memberService.deleteMember(username);
        return new ResponseEntity<>(new ResponseDto("보상 트랜잭션: 회원 삭제",null), HttpStatus.OK);
    }
    
}
