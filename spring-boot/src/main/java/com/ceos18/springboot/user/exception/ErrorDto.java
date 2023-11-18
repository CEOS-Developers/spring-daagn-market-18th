package com.ceos18.springboot.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.SQLException;

@Getter
@AllArgsConstructor
public class ErrorDto {
    private String errorCode;
    private String message;

    public ErrorDto(AppException e) {
        this.errorCode = e.getErrorCode().toString();
        this.message = e.getErrorCode().getMessage();
    }

    public ErrorDto(ErrorCode errorCode) {
        this.errorCode = errorCode.toString();
        this.message = errorCode.getMessage();
    }

}