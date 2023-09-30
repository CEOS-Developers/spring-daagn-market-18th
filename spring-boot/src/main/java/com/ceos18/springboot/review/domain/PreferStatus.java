package com.ceos18.springboot.review.domain;

import lombok.Getter;

@Getter
public enum PreferStatus {
    SOSO("별로에요"),
    GOOD("좋아요"),
    PERFECT("최고에요");

    private final String value;

    PreferStatus(String value) {
        this.value = value;
    }
}
