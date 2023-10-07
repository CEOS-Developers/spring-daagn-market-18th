package com.ceos18.springboot.post.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(){
        super("존재하지 않는 상품입니다.");
    }
}
