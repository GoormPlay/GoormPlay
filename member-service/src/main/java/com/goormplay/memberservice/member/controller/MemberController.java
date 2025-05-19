package com.goormplay.memberservice.member.controller;

import com.goormplay.memberservice.member.dto.Member.MemberProfileDto;
import com.goormplay.memberservice.member.dto.ResponseDto;
import com.goormplay.memberservice.member.dto.SignUpRequestDto;
import com.goormplay.memberservice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<ResponseDto> getMemberProfile(Authentication authentication) {
        log.info("Member Controller :  멤버 프로필 조회 시작");

        String member_Id = authentication.getName();
        MemberProfileDto memberProfileDto = memberService.findMemberProfile(member_Id);

        return new ResponseEntity<>(new ResponseDto("멤버 프로필 조회",memberProfileDto), HttpStatus.OK);
    }

    @PostMapping("/client")
    public String signUpMember(@RequestBody SignUpRequestDto dto) {
        log.info("Member Controller : 회원가입 시작 ");
        String memberId = memberService.joinMember(dto);
        log.info("memberId 반환 "+ memberId);
        return memberId;
    }

    @DeleteMapping("client/{username}")
    public ResponseEntity<ResponseDto> deleteMember(@PathVariable("username")String username){
        log.info("Member Controller : 회원가입 보상 트랜잭션, 유저 삭제 시작 ");
        memberService.deleteMember(username);
        return new ResponseEntity<>(new ResponseDto("보상 트랜잭션: 회원 삭제",null), HttpStatus.OK);
    }
    
}
