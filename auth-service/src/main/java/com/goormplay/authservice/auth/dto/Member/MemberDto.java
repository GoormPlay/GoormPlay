package com.goormplay.authservice.auth.dto.Member;

import com.goormplay.authservice.auth.entity.Role;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Data
@ToString
@NoArgsConstructor
public class MemberDto {//수정
    Long memberId;
    Role role;



    @Builder
    public MemberDto(Long memberId,Role role) {
        setMemberId(memberId);
        setRole(role);
    }
}
