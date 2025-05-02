package com.goormplay.authservice.auth.controller;

import com.goormplay.authservice.auth.dto.SignInRequestDTO;
import com.goormplay.authservice.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

        private final AuthService authService;

        @PostMapping("/signIn")
        public ResponseEntity<?> createToken(@RequestBody SignInRequestDTO dto){
            return ResponseEntity.ok(authService.createAccessToken(dto.getMemberId()));
        }


}
