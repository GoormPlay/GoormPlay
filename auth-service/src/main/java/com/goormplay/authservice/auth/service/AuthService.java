package com.goormplay.authservice.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;


    private final SecretKey secretKey;

    public AuthService(@Value("${service.jwt.secret-key}") String secretKey){
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);

        this.secretKey= Keys.hmacShaKeyFor(keyBytes);

    }//컴퓨터가 연산에 사용할 바이트 배열 형태의 키 객체로 변환


    public String createAccessToken(String member_id){
        return Jwts.builder()
                .claim("member_id", member_id)
                .claim("role","USER")
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+accessExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }




}
