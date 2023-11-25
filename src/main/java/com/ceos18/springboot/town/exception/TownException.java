package com.ceos18.springboot.town.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TownException extends RuntimeException {

    private final HttpStatus errorCode;

    public TownException(TownErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.getErrorCode();
    }
}
