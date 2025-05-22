package com.goormplay.memberservice.member.dto.Member;

import com.goormplay.memberservice.member.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private List<VideoDto> liked; // Video[] -> List<ContentCardDto>로 매핑
}
