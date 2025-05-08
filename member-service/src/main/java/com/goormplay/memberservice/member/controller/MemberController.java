package com.goormplay.memberservice.member.controller;

import com.goormplay.memberservice.member.dto.ResponseDto;
import com.goormplay.memberservice.member.dto.SignUpRequestDto;
import com.goormplay.memberservice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    public ResponseEntity<ResponseDto> test(Authentication authentication) {
        log.info("Member Service JWT test");

        String context_userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        log.info("context_userId : "+ context_userId);
        String authentication_userId = authentication.getName();
        log.info("authentication_userId : "+ authentication_userId);
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        log.info("roles : "+ roles);
        return new ResponseEntity<>(new ResponseDto("테스트 성공",null), HttpStatus.OK);
    }

    @PostMapping("/client")
    public Long signUpMember(@RequestBody SignUpRequestDto dto) {
        log.info("Member Service 회원가입 시작 ");
        Long memberId = memberService.joinMember(dto);
        log.info("memberId 반환 "+ memberId);
        return memberId;
    }

    @DeleteMapping("client/{username}")
    public ResponseEntity<ResponseDto> deleteMember(@PathVariable("username")String username){
        log.info("회원가입 보상 트랜잭션 유저 삭제 시작 ");
        memberService.deleteMember(username);
        return new ResponseEntity<>(new ResponseDto("보상 트랜잭션: 회원 삭제",null), HttpStatus.OK);
    }
    
}
