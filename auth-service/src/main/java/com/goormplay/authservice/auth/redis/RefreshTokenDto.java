package com.goormplay.authservice.auth.redis;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RefreshTokenDto {
    private final String refreshToken;
    private final String memberId;
}