package com.goormplay.subscribeservice.subscribe.dto.SubScribe;

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
    LocalDate subscriptionStartDate;
    LocalDate subscriptionEndDate;
    Boolean isCancelScheduled;
    Boolean isSubscribed;
}
