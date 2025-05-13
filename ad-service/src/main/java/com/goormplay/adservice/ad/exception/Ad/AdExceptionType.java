package com.goormplay.adservice.ad.exception.Ad;

import com.goormplay.adservice.ad.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum AdExceptionType implements BaseExceptionType {
    INVALID_AD_REQUEST(HttpStatus.NOT_FOUND, "요청에 해당하는 광고 정보가 없습니다.");
    private final HttpStatus httpStatus;
    private final String errorMessage;

    AdExceptionType(HttpStatus httpStatus, String errorMessage) {
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
