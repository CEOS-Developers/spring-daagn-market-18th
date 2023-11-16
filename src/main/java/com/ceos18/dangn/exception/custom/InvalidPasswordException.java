package com.ceos18.dangn.exception.custom;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("일치하지 않는 비밀번호입니다.");
    }
}
