package com.ceos18.springboot.member.dto.response;

import com.ceos18.springboot.member.domain.Member;
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

    public static LoginMemberResponse fromEntity(Member member, String accessToken) {
        return LoginMemberResponse.builder()
                .nickname(member.getNickname())
                .accessToken(accessToken)
                .build();
    }
}
