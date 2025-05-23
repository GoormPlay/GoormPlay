package com.goormplay.bffservice.bff.contoller;

import com.goormplay.bffservice.bff.dto.Member.MemberProfileDto;
import com.goormplay.bffservice.bff.dto.ResponseDto;
import com.goormplay.bffservice.bff.dto.SignUpRequestDto;
import com.goormplay.bffservice.bff.service.BFFService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/bff")
@RestController
public class BFFController {

    private final BFFService bffService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDto> joinBFFRequest(SignUpRequestDto dto) {
//        bffService.join();

        return new ResponseEntity<>(new ResponseDto("유저 계정 생성", null),
                HttpStatus.OK);
    }

//    @GetMapping("/profile")
//    public ResponseEntity<ResponseDto> getMemberProfile(Authentication authentication) {
//        log.info("Member Controller :  멤버 프로필 조회 시작");
//
//        String member_Id = authentication.getName();
//
//
//        return new ResponseEntity<>(new ResponseDto("멤버 프로필 조회", bffService.getMemberProfile(member_Id)), HttpStatus.OK);
//    }
}