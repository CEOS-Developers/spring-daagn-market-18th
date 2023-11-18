package com.ceos18.springboot.town.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TownErrorCode {

    TOWN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 Town을 찾을 수 없습니다.");

    private final HttpStatus errorCode;
    private final String errorMessage;

    TownErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
