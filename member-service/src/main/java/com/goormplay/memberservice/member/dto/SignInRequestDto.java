package com.goormplay.memberservice.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {
    @NotBlank
    private String memberId;
    @NotBlank
    private String memberPass;
}
