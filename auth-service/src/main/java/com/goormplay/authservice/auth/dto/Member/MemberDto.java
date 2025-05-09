package com.goormplay.authservice.auth.dto.Member;

import com.goormplay.authservice.auth.entity.Role;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Data
@ToString
@NoArgsConstructor
public class MemberDto {//수정
    String memberId;
    Role role;



    @Builder
    public MemberDto(String memberId,Role role) {
        setMemberId(memberId);
        setRole(role);
    }
}
