package com.goormplay.reviewservice.review.exception.Review;

import com.goormplay.reviewservice.review.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum ReviewExceptionType implements BaseExceptionType {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원 정보가 없습니다.");
    private final HttpStatus httpStatus;
    private final String errorMessage;

    ReviewExceptionType(HttpStatus httpStatus, String errorMessage) {
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
