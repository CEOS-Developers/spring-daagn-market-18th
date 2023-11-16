package com.ceos18.springboot.member.dto.request;

import lombok.Getter;

@Getter
public class LoginMemberRequest {

    private String email;

    private String password;
}
