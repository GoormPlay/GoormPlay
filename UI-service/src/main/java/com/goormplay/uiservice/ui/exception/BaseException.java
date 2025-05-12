package com.goormplay.uiservice.ui.exception;

public abstract class BaseException extends RuntimeException {
    public abstract BaseExceptionType getExceptionType();
}
