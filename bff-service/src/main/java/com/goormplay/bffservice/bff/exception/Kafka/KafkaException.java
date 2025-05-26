package com.goormplay.bffservice.bff.exception.Kafka;

import com.goormplay.bffservice.bff.exception.BaseException;
import com.goormplay.bffservice.bff.exception.BaseExceptionType;

public class KafkaException extends BaseException {
    private final BaseExceptionType exceptionType;

    public KafkaException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
