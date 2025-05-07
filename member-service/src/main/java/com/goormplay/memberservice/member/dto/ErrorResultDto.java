package com.goormplay.memberservice.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResultDto {
    String field;
    String message;
}