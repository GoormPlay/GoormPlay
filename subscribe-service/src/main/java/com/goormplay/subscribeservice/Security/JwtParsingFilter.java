package com.goormplay.subscribeservice.Security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Slf4j
public class JwtParsingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String token = resolveToken(request);
        log.info("JWT token detected: {}", token != null);
        if (token != null) {
            // 🔥 서명 검증 생략 (Base64 디코딩만 수행)
            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(chunks[1]));

            if (chunks.length != 3) {
                throw new ServletException("Invalid JWT structure");
            }

            // JSON 파싱 로직 (예: Jackson ObjectMapper 사용)
            JsonNode claims = parsePayload(payload);
            // SecurityContext에 인증 정보 설정
            log.info("claim - sub : " + claims.get("sub").asText());
            if (!claims.has("sub")) {
                throw new ServletException("Missing 'sub' claim");
            }
            Map<String, String> principal = new HashMap<>();
            principal.put("memberId", claims.get("sub").asText());
            principal.put("username", claims.get("username").asText());

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    extractAuthorities(claims)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer "))
                ? bearerToken.substring(7)
                : null;
    }
    private JsonNode parsePayload(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(payload); // JSON 문자열 → JsonNode 변환[2][5]
    }
    private Collection<? extends GrantedAuthority> extractAuthorities(JsonNode claims) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 권한 목록 추출
        if (claims.has("role")) {
            JsonNode roleNode = claims.get("role");
            authorities.add(new SimpleGrantedAuthority(roleNode.asText()));
        }else{
            authorities.add(new SimpleGrantedAuthority("USER"));
        }
        return authorities;
    }
}