package com.goormplay.adservice.ad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResultDto {
    String field;
    String message;
}