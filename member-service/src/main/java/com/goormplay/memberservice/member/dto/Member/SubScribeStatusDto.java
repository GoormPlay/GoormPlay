package com.goormplay.memberservice.member.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubScribeStatusDto {
    LocalDate subscription_start_date;
    LocalDate subscription_end_date;
    Boolean is_cancel_scheduled;
}
