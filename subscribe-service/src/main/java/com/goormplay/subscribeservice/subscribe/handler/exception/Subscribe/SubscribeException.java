package com.goormplay.subscribeservice.subscribe.handler.exception.Subscribe;


import com.goormplay.subscribeservice.subscribe.handler.exception.BaseException;
import com.goormplay.subscribeservice.subscribe.handler.exception.BaseExceptionType;

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