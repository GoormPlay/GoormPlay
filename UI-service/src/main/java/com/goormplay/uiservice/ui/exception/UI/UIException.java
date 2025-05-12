package com.goormplay.uiservice.ui.exception.UI;


import com.goormplay.uiservice.ui.exception.BaseException;
import com.goormplay.uiservice.ui.exception.BaseExceptionType;

public class UIException extends BaseException {
    private final BaseExceptionType exceptionType;

    public UIException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}