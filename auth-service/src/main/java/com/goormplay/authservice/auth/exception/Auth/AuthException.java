package com.goormplay.authservice.auth.exception.Auth;

import com.goormplay.authservice.auth.exception.BaseException;
import com.goormplay.authservice.auth.exception.BaseExceptionType;

public class AuthException extends BaseException {
    private final BaseExceptionType exceptionType;

    public AuthException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}