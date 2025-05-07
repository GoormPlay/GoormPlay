package com.goormplay.authservice.auth.exception.Jwt;

import com.goormplay.authservice.auth.exception.BaseException;
import com.goormplay.authservice.auth.exception.BaseExceptionType;


public class JwtException extends BaseException {
    private final BaseExceptionType exceptionType;

    public JwtException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}