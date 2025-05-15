package com.goormplay.adadminservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RechargeRequestDTO {
    private String advertiserId;
    private Long amount;
}
