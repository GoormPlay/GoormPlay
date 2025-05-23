package com.goormplay.bffservice.bff.handler;

import lombok.Data;

import java.util.Map;
@Data
public class ErrorLogEvent {
    private String serviceName;
    private String timestamp;
    private String errorType;
    private String errorMessage;
    private String stackTrace;
    private Map<String, Object> requestInfo; // 요청 URL, HTTP Method 등 추가 정보
    private String HttpMethod;
    private String RequestUri;

    // Constructor, Getters, Setters, toString
}