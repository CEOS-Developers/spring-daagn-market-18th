package com.daagn.clonestudy.member.dto.request;

import com.daagn.clonestudy.member.domain.Member;
import java.util.Collections;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JoinRequest {
    private String phoneNumber;
    private String password;
    private String nickname;
    private String town;

    public Member toEntity(String encodedPassword){
        return Member.builder()
            .phoneNumber(phoneNumber)
            .password(encodedPassword)
            .nickname(nickname)
            .town(town)
            .roles(Collections.singletonList("ROLE_USER"))
            .build();
    }
}
