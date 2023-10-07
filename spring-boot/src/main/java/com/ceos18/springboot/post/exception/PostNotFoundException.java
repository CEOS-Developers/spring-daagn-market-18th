package com.ceos18.springboot.post.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(){
        super("상품이 존재하지 않습니다.");
    }
}
