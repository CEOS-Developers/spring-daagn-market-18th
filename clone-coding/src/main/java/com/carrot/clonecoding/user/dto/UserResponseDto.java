package com.carrot.clonecoding.user.dto;

import com.carrot.clonecoding.user.domain.model.User;
import lombok.Getter;
@Getter
public class UserResponseDto {
    private String nickName;
    private String email;
    private String phonenum;
    private double temperature;
    private double responseRate;
    private double retradingRate;

    public UserResponseDto(User user) {
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.phonenum = user.getPhonenum();
        this.temperature = user.getTemperature();
        this.responseRate = user.getResponseRate();
        this.retradingRate = user.getRetradingRate();
    }
}
