package com.goormplay.memberservice.member.exception.Member;

import com.goormplay.memberservice.member.exception.BaseException;
import com.goormplay.memberservice.member.exception.BaseExceptionType;

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