package com.daagn.clonestudy.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String phoneNumber;
    private String password;
}
