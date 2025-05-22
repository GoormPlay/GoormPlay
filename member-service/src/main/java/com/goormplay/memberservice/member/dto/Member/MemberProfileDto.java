package com.goormplay.memberservice.member.dto.Member;

import com.goormplay.memberservice.member.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileDto {
    String username;
    Gender gender;
    Integer age;
    LocalDate subscription_start_date;
    LocalDate subscription_end_date;
    Boolean isCancelScheduled;
    Boolean isSubscribed;
    //아마 유저 좋아요 정보? 추천 영화도 담아서 보내야 할듯?
}
