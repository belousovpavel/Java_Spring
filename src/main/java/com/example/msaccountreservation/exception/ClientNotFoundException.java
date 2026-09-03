package com.example.msaccountreservation.exception;

import lombok.Getter;

@Getter
public class ClientNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ClientNotFoundException(String message) {
        super(message);
        this.errorCode = ErrorCode.CLIENT_NOT_FOUND;
    }
}
