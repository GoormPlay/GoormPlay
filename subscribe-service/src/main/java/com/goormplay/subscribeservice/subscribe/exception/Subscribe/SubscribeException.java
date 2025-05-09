package com.goormplay.subscribeservice.subscribe.exception.Subscribe;


import com.goormplay.subscribeservice.subscribe.exception.BaseException;
import com.goormplay.subscribeservice.subscribe.exception.BaseExceptionType;

public class SubscribeException extends BaseException {
    private final BaseExceptionType exceptionType;

    public SubscribeException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}