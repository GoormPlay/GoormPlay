package com.goormplay.authservice.auth.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.goormplay.authservice.auth.client.MemberClient;
import com.goormplay.authservice.auth.dto.Member.MemberDto;
import com.goormplay.authservice.auth.dto.SignInRequestDto;
import com.goormplay.authservice.auth.dto.SignUpRequestDto;
import com.goormplay.authservice.auth.entity.Auth;
import com.goormplay.authservice.auth.entity.Role;
import com.goormplay.authservice.auth.exception.Auth.AuthException;
import com.goormplay.authservice.auth.exception.Jwt.JwtException;
import com.goormplay.authservice.auth.exception.Jwt.JwtExceptionType;
import com.goormplay.authservice.auth.redis.RefreshTokenDto;
import com.goormplay.authservice.auth.repository.AuthRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

import static com.goormplay.authservice.auth.exception.Auth.AuthExceptionType.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;
    private final MemberClient memberClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public String signIn(SignInRequestDto dto) {
        log.info("Auth Service - AuthServiceImpl - 로그인 시작");
        Auth auth = authRepository.findByUsername(dto.getUsername()).orElseThrow(()->new AuthException(NOT_FOUND_MEMBER));
        String memberPass = auth.getPassword();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), memberPass)) {
            throw new AuthException(WRONG_PASSWORD);
        }

        auth.setLastLoginAt(LocalDateTime.now());

        return createJwt(MemberDto.builder().
                memberId(auth.getId()).
                role(auth.getRole())
                .build());
    }

    @Override
    @Transactional
    public void signUp(SignUpRequestDto dto) {
        log.info("Auth Service - AuthServiceImpl - 회원가입 시작");
        if(authRepository.existsByUsername(dto.getUsername())) throw new AuthException(ALREADY_EXIST_MEMBER);

        try {
           Long memberId = memberClient.signUpMember(dto);
           log.info("memberID : :::::::   "+ memberId);
            Auth auth = Auth.builder()
                    .id(memberId)
                    .username(dto.getUsername())
                    .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                    .role(Role.USER)
                    .createdAt(LocalDateTime.now())
                    .build();
            authRepository.save(auth);

        } catch (Exception e) {
            // 실패 시 보상 트랜잭션 실행
            memberClient.deleteMember(dto.getUsername());
            throw new AuthException(SIGN_UP_FAIL);
        }
    }

    @Override
    public void deleteTransaction(String username) {
        log.info("Auth Service - AuthServiceImpl - deleteTransaction 시작");
        memberClient.deleteMember(username);
    }

    @Override
    public String createJwt(MemberDto memberDto) {
        log.info("Auth Service - AuthServiceImpl - 토큰 발급");
        return  jwtUtil.createJwt(memberDto);
    }

    @Override
    public String tokenRefresh() {
        log.info("Auth Service - AuthServiceImpl - 토큰 재발급");
        
        //쿠키에서 refresh token 받아오기
        String refreshToken = jwtUtil.getRefreshTokenFromCookie();

        if (refreshToken == null) {
            throw new JwtException(JwtExceptionType.TOKEN_NULL);
        }
        log.info("Auth Service - AuthServiceImpl - 리프레시 토큰 유효성 검사");
        jwtUtil.isValidToken(refreshToken, JwtUtil.REFRESH_TOKEN_SUBJECT);

        // refresh token 에서 유저 audience값 가져오기
        DecodedJWT payload = jwtUtil.getDecodedJWT(refreshToken);
        Long memberId = payload.getClaim("memberId").asLong();

        log.info("Auth Service - AuthServiceImpl - 레디스 확인");
        // redis에 refresh 토큰이 있는지 체크
        RefreshTokenDto refreshTokenDto = jwtUtil.getRefreshTokenFromRedis(refreshToken);

        Auth auth = authRepository.findById(memberId).orElseThrow(()->new AuthException(NOT_FOUND_MEMBER));
        return jwtUtil.createJwt(MemberDto.builder().memberId(auth.getId()).build()); // access token return
    }

    @Override
    public void logout() {
        log.info("Auth Service - AuthServiceImpl - 로그아웃 시작 ");
        String refreshToken = jwtUtil.getRefreshTokenFromCookie();
        jwtUtil.deleteRefreshToken(refreshToken);
        deleteRefreshTokenCookie();
    }

    private void deleteRefreshTokenCookie() {
        log.info("Auth Service - AuthServiceImpl - 쿠키에서 리프레시 토큰 삭제 시작 ");
        HttpServletResponse response
                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Cookie cookie = new Cookie(JwtUtil.REFRESH_TOKEN_SUBJECT, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
