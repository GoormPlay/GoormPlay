package com.goormplay.authservice.auth.dto.Member;

import com.goormplay.authservice.auth.entity.Role;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Data
@ToString
@NoArgsConstructor
public class MemberDto {
    String username;
    Role role;



    @Builder
    public MemberDto(String username,Role role) {
        setUsername(username);
        setRole(role);
    }
}
