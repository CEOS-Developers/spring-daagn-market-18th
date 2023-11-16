package com.ceos18.dangn.exception.custom;

public class UserDuplicatedException extends RuntimeException {
    public UserDuplicatedException() {
        super("이미 존재하는 사용자입니다");
    }
}
