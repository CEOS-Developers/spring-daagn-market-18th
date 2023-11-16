package com.daagn.clonestudy.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
    private String accessToken;

    @Builder
    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
