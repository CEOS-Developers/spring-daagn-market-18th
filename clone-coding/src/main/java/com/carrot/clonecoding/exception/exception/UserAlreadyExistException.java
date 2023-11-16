package com.carrot.clonecoding.exception.exception;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(){
        super("이미 존재하는 유저입니다.");
    }
}
