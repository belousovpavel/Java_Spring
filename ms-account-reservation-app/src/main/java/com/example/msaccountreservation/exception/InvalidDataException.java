package com.example.msaccountreservation.exception;

import lombok.Getter;

@Getter
public class InvalidDataException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidDataException(String message) {
        super(message);
        this.errorCode = ErrorCode.VALIDATION_ERROR;
    }
}
