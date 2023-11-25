package com.ceos18.springboot.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequest {
    @NotNull(message = "로그인을 위한 이메일을 입력해주세요.")
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String pwd;

    @Builder
    public UserLoginRequest(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }
}