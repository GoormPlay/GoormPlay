package com.goormplay.authservice.auth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignInResponseDto {
    String accessToken;
    String username;
}
