package com.ceos18.springboot.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER("회원"),
    ROLE_ADMIN("관리자");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
