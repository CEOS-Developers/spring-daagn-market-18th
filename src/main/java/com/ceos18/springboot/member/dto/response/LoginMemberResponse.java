package com.ceos18.springboot.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginMemberResponse {

    private final String nickname;
    private final String accessToken;

    @Builder
    public LoginMemberResponse(String nickname, String accessToken) {
        this.nickname = nickname;
        this.accessToken = accessToken;
    }

    public static LoginMemberResponse fromEntity(String nickname, String accessToken) {
        return LoginMemberResponse.builder()
                .nickname(nickname)
                .accessToken(accessToken)
                .build();
    }
}
