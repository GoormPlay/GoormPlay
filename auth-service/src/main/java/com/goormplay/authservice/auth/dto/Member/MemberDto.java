package com.goormplay.authservice.auth.dto.Member;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Data
@ToString
@NoArgsConstructor
public class MemberDto {
    String username;




    @Builder
    public MemberDto(String username) {
        setUsername(username);
    }
}
