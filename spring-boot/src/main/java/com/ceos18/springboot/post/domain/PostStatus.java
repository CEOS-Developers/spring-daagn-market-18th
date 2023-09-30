package com.ceos18.springboot.post.domain;

import lombok.Getter;

@Getter
public enum PostStatus {
    SALE("판매중"),
    TRANSACTION("예약중"),
    COMPLETE("거래완료");

    private final String value;

    PostStatus(String value) {
        this.value = value;
    }
}


