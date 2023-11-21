package com.ceos18.springboot.user.dto.request;

import com.ceos18.springboot.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequest {
    private String email;
    private String pwd;
    private String imgUrl;
    private String name;
    private String nick;
    private String phone;

//    private Town town;

    @Builder
    public UserJoinRequest(String email, String pwd, String imgUrl, String name, String nick, String phone) {
        this.email = email;
        this.pwd = pwd;
        this.imgUrl = imgUrl;
        this.name = name;
        this.nick = nick;
        this.phone = phone;
    }

    public User toEntity(String encodedPwd) {
        return User.builder()
                .email(email)
                .pwd(encodedPwd)
                .imgUrl(imgUrl)
                .name(name)
                .nick(nick)
                .phone(phone)
                .build();
    }
}
