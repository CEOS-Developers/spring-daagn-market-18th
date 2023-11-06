package com.ceos18.springboot.member.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("회원 정보가 존재하지 않습니다.");
    }

    public MemberNotFoundException(Long id) {
        super("요청한 id 값 " + id + "에 해당하는 회원 정보가 존재하지 않습니다.");
    }
}
