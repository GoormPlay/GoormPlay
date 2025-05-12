package com.goormplay.uiservice.ui.exception.UI;

// import com.goormplay.memberservice.member.exception.BaseExceptionType;
import com.goormplay.uiservice.ui.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum UIExceptionType implements BaseExceptionType {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원 정보가 없습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다."),
    ALREADY_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "존재하는 회원 아이디 입니다.");
    private final HttpStatus httpStatus;
    private final String errorMessage;

    UIExceptionType(HttpStatus httpStatus, String errorMessage) {
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
