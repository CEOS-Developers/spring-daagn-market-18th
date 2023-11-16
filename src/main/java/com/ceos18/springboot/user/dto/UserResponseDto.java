package com.ceos18.springboot.user.dto;

import com.ceos18.springboot.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;

    private String email;

    private String nickname;

    private String phone;

    private String imgUrl;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .imgUrl(user.getImgUrl())
                .build();
    }
}
