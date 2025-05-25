package com.example.api.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExectionReponse {
    private final Integer status;
    private final String detail;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ExectionReponse(Integer status, String detail) {
        this.status = status;
        this.detail = detail;
    }
}
