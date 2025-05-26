package com.goormplay.subscribeservice.subscribe.exception.Subscribe;

import com.goormplay.subscribeservice.subscribe.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum SubscribeExceptionType implements BaseExceptionType {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원 정보가 없습니다."),
    ALREADY_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "존재하는 회원 아이디 입니다."),
    JSON_PARSING_EX(HttpStatus.BAD_REQUEST , "JSON 역직렬화 실패"),
    EVENT_PROCESSING_FAILED (HttpStatus.SERVICE_UNAVAILABLE , "이벤트 처리 실패");
    private final HttpStatus httpStatus;
    private final String errorMessage;

    SubscribeExceptionType(HttpStatus httpStatus, String errorMessage) {
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
