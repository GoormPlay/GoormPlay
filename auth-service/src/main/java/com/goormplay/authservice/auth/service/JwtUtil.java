package com.goormplay.authservice.auth.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.goormplay.authservice.auth.dto.Member.MemberDto;
import com.goormplay.authservice.auth.exception.Jwt.JwtException;
import com.goormplay.authservice.auth.exception.Jwt.JwtExceptionType;
import com.goormplay.authservice.auth.redis.RefreshToken;
import com.goormplay.authservice.auth.redis.RefreshTokenDto;
import com.goormplay.authservice.auth.redis.RefreshTokenRepository;

import com.auth0.jwt.JWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;
    @Value("${service.jwt.refresh-expiration}")
    private Long refreshExpiration;

    @Value("${service.jwt.secret-key}")
    private String secret;
    private Algorithm key;

    @PostConstruct
    public void setKey() {
        key = Algorithm.HMAC256(secret);
    }

    private final RefreshTokenRepository refreshTokenRepository;

    public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    static final String NICKNAME_CLAIM = "nickname";
    static final String BEARER = "Bearer ";


    public String createJwt(MemberDto memberDto) {
        // 토큰 발급
        String accessToken = getAccessToken(memberDto);
        String refreshToken = getRefreshToken(memberDto);

        // 리프레쉬 토큰을 redis에 저장
        RefreshToken savedRefreshToken = refreshTokenRepository.save(new RefreshToken(refreshToken, memberDto.getIdx()));

        // cookie에 Refresh token 담기
        setRefreshTokenToCookie(refreshToken);

        if (refreshTokenRepository.findById(savedRefreshToken.getRefreshToken()).isEmpty()) {
            throw new JwtException(JwtExceptionType.TOKEN_SAVE_FAIL);
        }

        // Access token 리턴
        return accessToken;
    }

    // 현재 accessToken : 10분/ refreshToken : 1시간 => 배포시 늘려야됨
    public String getAccessToken(MemberDto memberDto) {
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withAudience(memberDto.getIdx().toString())
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusMinutes(accessExpiration)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .sign(key);
    }


    public String getRefreshToken(MemberDto memberDto) {
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withAudience(memberDto.getIdx().toString())
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusMinutes(refreshExpiration)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .sign(key);
    }


    public void isValidForm(String token){
        // 토큰이 들어왔는지
        if (token == null) {
            throw new JwtException(JwtExceptionType.TOKEN_NULL);
        }

        // 토큰이 "Bearer "로 시작하는지
        if (!token.startsWith(BEARER)) {
            throw new JwtException(JwtExceptionType.NOT_START_WITH_BEARER);
        }

        // 토큰이 "Bearer " 이후로 존재하는지
        if (token.length() < 8) {
            throw new JwtException(JwtExceptionType.TOKEN_TOO_SHORT);
        }
    }


    // 1. 토큰 타입
    // 2. 토큰 서명
    // 3. 토큰 발행 대상자
    public void isValidToken(String token, String tokenType) {
        try {
            DecodedJWT decodedJWT = JWT.require(key)
                    .withSubject(tokenType)
                    .build()
                    .verify(token);

            if (decodedJWT.getAudience().isEmpty()) {

                throw new JWTVerificationException("NotValidToken");
            }
        } catch (TokenExpiredException e) {
            // 토큰 만료시 -> 401 + 클라이언트에서 억세스 토큰 재발급
            throw new JwtException(JwtExceptionType.TOKEN_EXPIRED);
        } catch (JWTVerificationException e) {
            // 다른 경우는 모두 인증 실패
            throw new JwtException(JwtExceptionType.JWT_VERIFICATION_EXCEPTION);
        }
    }


    // 토큰 디코딩
    public DecodedJWT getDecodedJWT(String token) {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            throw new JwtException(JwtExceptionType.DECODE_FAIL);
        }
    }


    public void setRefreshTokenToCookie(String refreshToken) {
        HttpServletRequest request
                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response
                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_SUBJECT, refreshToken);
        refreshTokenCookie.setMaxAge((refreshExpiration).intValue() * 60);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(request.isSecure()); // true로 하면 항상 https만 가능
        refreshTokenCookie.setPath("/api");

        response.addCookie(refreshTokenCookie);
    }


    public String getRefreshTokenFromCookie() {
        HttpServletRequest request
                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Cookie refreshTokenCookie = WebUtils.getCookie(request, REFRESH_TOKEN_SUBJECT);
        if (refreshTokenCookie != null) {
            return refreshTokenCookie.getValue();
        } else {
            throw new JwtException(JwtExceptionType.TOKEN_NULL);
        }
    }

    public RefreshTokenDto getRefreshTokenFromRedis(String refreshToken) {
        RefreshToken rt = refreshTokenRepository
                .findById(refreshToken)
                .orElseThrow(() -> new JwtException(JwtExceptionType.TOKEN_EXPIRED));
        return RefreshTokenDto.builder().refreshToken(rt.getRefreshToken()).memberId(rt.getMemberId()).build();
    }

    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }
}
