package com.goormplay.subscribeservice.subscribe.handler.exception;

public abstract class BaseException extends RuntimeException {
    public abstract BaseExceptionType getExceptionType();
}
