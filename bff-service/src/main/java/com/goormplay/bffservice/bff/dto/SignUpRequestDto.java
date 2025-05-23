package com.goormplay.bffservice.bff.dto;

import com.goormplay.bffservice.bff.dto.Member.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "사용자 이름은 필수입니다.")
    @Size(min = 4, max = 20, message = "사용자 이름은 4자 이상 20자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "사용자 이름은 영문자와 숫자만 포함할 수 있습니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 30, message = "비밀번호는 8자 이상 30자 이하여야 합니다.")
    private String password;

    @NotNull(message = "성별은 필수입니다.")
    private Gender gender;

    @Min(value = 1, message = "나이는 1살 이상이어야 합니다.")
    @Max(value = 150, message = "나이는 150살을 초과할 수 없습니다.")
    private int age;
}
