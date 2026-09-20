package com.example.msaccountreservation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    VALIDATION_ERROR("VALIDATION_ERROR", "Невалидные данные", 400),

    CLIENT_NOT_FOUND("CLIENT_NOT_FOUND", "Клиент не найден", 404),

    CLIENT_ALREADY_EXISTS("CLIENT_ALREADY_EXISTS", "Клиент с таким id уже существует", 409);

    private final String code;
    private final String description;
    private final int statusCode;

}
