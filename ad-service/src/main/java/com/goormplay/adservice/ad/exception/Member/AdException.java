package com.goormplay.adservice.ad.exception.Member;


import com.goormplay.adservice.ad.exception.BaseException;
import com.goormplay.adservice.ad.exception.BaseExceptionType;

public class AdException extends BaseException {
    private final BaseExceptionType exceptionType;

    public AdException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}