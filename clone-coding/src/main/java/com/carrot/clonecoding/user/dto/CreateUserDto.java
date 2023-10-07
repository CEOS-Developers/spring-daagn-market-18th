package com.carrot.clonecoding.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateUserDto {
    private String nickName;
    private String password;
    private String email;
    private String phonenum;
}
