package com.example.msaccountreservation.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int statusCode;
    private String error;
    private String errorCode;
    private String errorDescription;
    private String message;
    private String path;

    public static ErrorResponse of(ErrorCode errorCode, String message, String path) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(errorCode.getStatusCode())
                .error(HttpStatus.valueOf(errorCode.getStatusCode()).getReasonPhrase())
                .errorCode(errorCode.getCode())
                .errorDescription(errorCode.getDescription())
                .message(message)
                .path(path)
                .build();
    }

}
