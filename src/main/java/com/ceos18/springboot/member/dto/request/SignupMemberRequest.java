package com.ceos18.springboot.member.dto.request;

import com.ceos18.springboot.member.domain.Member;
import lombok.Getter;

@Getter
public class SignupMemberRequest {

    private String password;

    private String nickname;

    private String phone;

    private String email;

    private String imageUrl;

    public Member toEntity() {
        return Member.builder()
                .password(this.password)
                .nickname(this.nickname)
                .phone(this.phone)
                .email(this.email)
                .imageUrl(this.imageUrl)
                .build();
    }
}
