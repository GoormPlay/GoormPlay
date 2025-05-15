package com.goormplay.adadminservice.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AdStatus {
    ONGOING(1),
    ENDED(2);

    private final int statusCode;

    AdStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public static AdStatus fromStatusCode(int statusCode) {
        return Arrays.stream(AdStatus.values())
                .filter(status -> status.getStatusCode() == statusCode)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid status code " + statusCode));
    }
}
