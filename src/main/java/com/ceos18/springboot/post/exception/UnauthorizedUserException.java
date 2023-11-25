package com.ceos18.springboot.post.exception;

public class UnauthorizedUserException extends RuntimeException{
    public UnauthorizedUserException(){ super("권한이 없는 유저입니다.");}
}
