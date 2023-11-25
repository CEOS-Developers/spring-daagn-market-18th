package com.ceos18.dangn.user.dto;

import com.ceos18.dangn.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileDto {
    private String profileImage;
    private String nickname;
    private double manners;

    @Builder
    public UserProfileDto(User user) {
        this.profileImage = user.getProfileImage();
        this.nickname = user.getNickname();
        this.manners = user.getManners();
    }
}
