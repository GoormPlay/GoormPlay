package com.goormplay.reviewservice.review.exception.Review;


import com.goormplay.reviewservice.review.exception.BaseException;
import com.goormplay.reviewservice.review.exception.BaseExceptionType;

public class ReviewException extends BaseException {
    private final BaseExceptionType exceptionType;

    public ReviewException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}