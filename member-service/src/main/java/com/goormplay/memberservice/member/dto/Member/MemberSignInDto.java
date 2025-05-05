package com.goormplay.memberservice.member.dto.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
public class MemberSignInDto {
    Long idx;
    String pass;

    public void setId(Long idx) {
        if(idx>=0){
            this.idx = idx;
        }
    }

    public void setPass(String pass) {
        this.pass = pass;
    }



    @Builder
    public MemberSignInDto(Long idx,String pass) {
        setId(idx);
        setPass(pass);
    }
}