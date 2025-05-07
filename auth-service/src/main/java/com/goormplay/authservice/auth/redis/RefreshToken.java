package com.goormplay.authservice.auth.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 864000)
@Getter
@AllArgsConstructor
public class RefreshToken {
    @Id
    private String refreshToken;
    private String username;
}