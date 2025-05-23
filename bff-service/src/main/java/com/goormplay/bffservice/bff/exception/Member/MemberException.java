package com.goormplay.bffservice.bff.exception.Member;


import com.goormplay.bffservice.bff.exception.BaseException;
import com.goormplay.bffservice.bff.exception.BaseExceptionType;

public class MemberException extends BaseException {
    private final BaseExceptionType exceptionType;

    public MemberException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}