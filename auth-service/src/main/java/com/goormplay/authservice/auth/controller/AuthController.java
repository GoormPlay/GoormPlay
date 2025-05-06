package com.goormplay.authservice.auth.controller;

import com.goormplay.authservice.auth.dto.ResponseDto;
import com.goormplay.authservice.auth.dto.SignInRequestDto;
import com.goormplay.authservice.auth.dto.SignUpRequestDto;
import com.goormplay.authservice.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> signIn(@Valid @RequestBody SignInRequestDto dto) {
        log.info("Auth Service 로그인 시작");
        String accessToken = authService.signIn(dto);

        return new ResponseEntity<>(new ResponseDto("로그인", accessToken), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody SignUpRequestDto dto) {
        log.info("Auth Service 회원가입 시작");
        try{
            authService.signUp(dto);
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseDto("회원가입 실패", dto.getUsername()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseDto("회원가입", dto.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<ResponseDto> tokenRefresh() {
        log.info("Auth Service 토큰 리프레시 시작");
        String accessToken = authService.tokenRefresh();
        return new ResponseEntity<>(new ResponseDto("토큰 리프레시", accessToken), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout() {
        log.info("Auth Service 로그아웃 시작");
        authService.logout();
        return new ResponseEntity<>(new ResponseDto("로그아웃", null), HttpStatus.OK);
    }
}
