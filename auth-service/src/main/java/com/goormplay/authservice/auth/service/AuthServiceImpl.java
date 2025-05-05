package com.goormplay.authservice.auth.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.goormplay.authservice.auth.client.MemberClient;
import com.goormplay.authservice.auth.dto.Member.MemberDto;
import com.goormplay.authservice.auth.dto.Member.MemberSignInDto;
import com.goormplay.authservice.auth.dto.SignInRequestDto;
import com.goormplay.authservice.auth.dto.SignUpRequestDto;
import com.goormplay.authservice.auth.entity.Auth;
import com.goormplay.authservice.auth.exception.Auth.AuthException;
import com.goormplay.authservice.auth.exception.Jwt.JwtException;
import com.goormplay.authservice.auth.exception.Jwt.JwtExceptionType;
import com.goormplay.authservice.auth.redis.RefreshTokenDto;
import com.goormplay.authservice.auth.repository.AuthRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

import static com.goormplay.authservice.auth.exception.Auth.AuthExceptionType.NOT_FOUND_MEMBER;
import static com.goormplay.authservice.auth.exception.Auth.AuthExceptionType.WRONG_PASSWORD;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;
    private final MemberClient memberClient;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public String signIn(SignInRequestDto dto) {
        Auth auth = authRepository.findByUsername(dto.getMemberId()).orElseThrow(()->new AuthException(NOT_FOUND_MEMBER));
        String memberPass = auth.getPassword();
        if (!bCryptPasswordEncoder.matches(dto.getMemberPass(), memberPass)) {
            throw new AuthException(WRONG_PASSWORD);
        }

        auth.setLastLoginAt(LocalDateTime.now());

        return createJwt(MemberDto.builder().
                idx(auth.getMemberIndex()).build());
    }

    @Override
    @Transactional
    public void signUp(SignUpRequestDto dto) {
        memberClient.singUpMember(dto);
        authRepository.save(Auth.builder().
                username(dto.getMemberId()).
                password(bCryptPasswordEncoder.encode(dto.getMemberPass())).
                createdAt(LocalDateTime.now()).build());
    }

    @Override
    public String createJwt(MemberDto memberDto) {
        return  jwtUtil.createJwt(memberDto);
    }

    @Override
    public String tokenRefresh() {
        //쿠키에서 refresh token 받아오기
        String refreshToken = jwtUtil.getRefreshTokenFromCookie();

        if (refreshToken == null) {
            throw new JwtException(JwtExceptionType.TOKEN_NULL);
        }

        jwtUtil.isValidToken(refreshToken, JwtUtil.REFRESH_TOKEN_SUBJECT);

        // refresh token 에서 유저 audience값 가져오기
        DecodedJWT payload = jwtUtil.getDecodedJWT(refreshToken);
        String memberId = payload.getAudience().get(0);

        // redis에 refresh 토큰이 있는지 체크
        RefreshTokenDto refreshTokenDto = jwtUtil.getRefreshTokenFromRedis(refreshToken);

        MemberDto memberDto = memberClient.getMember(memberId);
        return jwtUtil.createJwt(memberDto); // access token return
    }

    @Override
    public void logout() {
        String refreshToken = jwtUtil.getRefreshTokenFromCookie();
        jwtUtil.deleteRefreshToken(refreshToken);
        deleteRefreshTokenCookie();
    }

    private void deleteRefreshTokenCookie() {
        HttpServletResponse response
                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Cookie cookie = new Cookie(JwtUtil.REFRESH_TOKEN_SUBJECT, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
