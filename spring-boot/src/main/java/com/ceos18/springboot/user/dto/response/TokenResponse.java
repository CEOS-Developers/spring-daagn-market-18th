package com.ceos18.springboot.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class TokenResponse {
    private String accessToken;

    @Builder
    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static TokenResponse from(String accessToken) {
        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

}
