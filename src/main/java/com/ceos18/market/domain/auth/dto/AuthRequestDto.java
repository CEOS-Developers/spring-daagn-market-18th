package com.ceos18.market.domain.auth.dto;

import com.ceos18.market.database.enums.StatusCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthRequestDto {
    private String userNo;
    private String nickName;
    private String phoneNumber;
    private String userImageUrl;
    private String address;
    private String userAgent;
    private StatusCode marketingYN;
}
