package com.ceos18.springboot.user.dto;

public class UserProfileImageDto {

    private String imgUrl;

    public UserProfileImageDto(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getimgUrl() {
        return imgUrl;
    }

    public void setimgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
