package com.goormplay.memberservice.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    private String memberId;
    private String memberPass;
    private String memberGender;
    private String memberAge;
}
