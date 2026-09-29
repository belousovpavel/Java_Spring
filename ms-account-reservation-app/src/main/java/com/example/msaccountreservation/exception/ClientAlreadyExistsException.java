package com.example.msaccountreservation.exception;

import lombok.Getter;

@Getter
public class ClientAlreadyExistsException extends RuntimeException {

    private final ErrorCode errorCode;

    public ClientAlreadyExistsException(String message) {
        super(message);
        this.errorCode = ErrorCode.CLIENT_ALREADY_EXISTS;
    }
}
