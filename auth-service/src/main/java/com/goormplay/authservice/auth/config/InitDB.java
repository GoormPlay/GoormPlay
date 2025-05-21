package com.goormplay.authservice.auth.config;

import com.goormplay.authservice.auth.dto.SignUpRequestDto;
import com.goormplay.authservice.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDB {
    private final AuthService authService;

}
