package com.ceos18.springboot.member.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberException extends RuntimeException {

    private final HttpStatus errorCode;

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.getErrorCode();
    }

    public MemberException(MemberErrorCode errorCode, Long id) {
        super(errorCode.getErrorMessage() + " 요청받은 id : " + id);
        this.errorCode = errorCode.getErrorCode();
    }

    public MemberException(MemberErrorCode errorCode, String email) {
        super(errorCode.getErrorMessage() + " 요청받은 email : " + email);
        this.errorCode = errorCode.getErrorCode();
    }
}
