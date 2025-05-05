package com.goormplay.memberservice.member.exception.Member;

import com.goormplay.memberservice.member.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원 정보가 없습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다.");
    private final HttpStatus httpStatus;
    private final String errorMessage;

    MemberExceptionType(HttpStatus httpStatus, String errorMessage) {
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
