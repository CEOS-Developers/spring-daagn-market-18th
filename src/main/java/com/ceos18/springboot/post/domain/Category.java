package com.ceos18.springboot.post.domain;

import com.ceos18.springboot.post.exception.PostErrorCode;
import com.ceos18.springboot.post.exception.PostException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {

    SHIRT,
    PANTS,
    SHOES;

    public static Category getCategoryByName(String name) {
        return Arrays.stream(Category.values())
                .filter(category -> category.name().equalsIgnoreCase(name))
                .findAny().orElseThrow(() -> new PostException(PostErrorCode.CATEGORY_NOT_FOUND));
    }
}
