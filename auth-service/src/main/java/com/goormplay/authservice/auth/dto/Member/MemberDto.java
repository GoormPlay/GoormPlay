package com.goormplay.authservice.auth.dto.Member;

import com.goormplay.authservice.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {//수정
    String memberId;
    Role role;

}
