package com.ceos18.springboot.post.domain;

import lombok.Getter;

@Getter
public enum Status {

    SELLING("판매중"),
    RESERVED("예약중"),
    SOLDOUT("거래완료");

    private final String value;

    Status(String value) {
        this.value = value;
    }
}
