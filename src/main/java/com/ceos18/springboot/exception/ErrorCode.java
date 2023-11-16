package com.ceos18.springboot.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보를 찾지 못했습니다.", "email 과 password 를 올바르게 입력했는지 확인해주세요"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "글을 찾지 못했습니다.", "존재하는 글인지 확인해주세요."),
    FORBIDDEN_ARTICLE(HttpStatus.FORBIDDEN, "게시글 수정, 삭제에 대한 권한이 없습니다.", "잘못된 접근입니다. 입력값을 확인해주세요."),
    VALUE_IS_NONNULL(HttpStatus.BAD_REQUEST, "필수값을 입력하지 않았습니다.", "null 값이 허용되지 않으므로 반드시 값을 전달해주세요."),
    ALREADY_MEMBER(HttpStatus.CONFLICT, "이미 존재하는 유저 정보입니다.", "다른 이메일 혹은 닉네임을 사용해야합니다.");

    private final HttpStatus httpStatus;
    private final String message;
    private final String solution;

}