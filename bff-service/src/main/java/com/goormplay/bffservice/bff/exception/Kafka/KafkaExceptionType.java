package com.goormplay.bffservice.bff.exception.Kafka;

import com.goormplay.bffservice.bff.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum KafkaExceptionType implements BaseExceptionType {
    USER_JOIN_FAIL(HttpStatus.BAD_REQUEST, "회원 가입 실패.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    KafkaExceptionType(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
