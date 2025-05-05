package com.goormplay.memberservice.member.dto.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
public class MemberDto {
    Long idx;

    public void setIdx(Long idx) {
        if(idx>=0){
            this.idx = idx;
        }
    }


    @Builder
    public MemberDto(Long idx) {
        setIdx(idx);
    }
}
