package com.ceos18.market.database;

import com.ceos18.market.database.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {
    public static final String TABLE_NAME = "USER_REFRESH_TOKEN";

    @Id
    @Column(name = "REFRESH_TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;

    @Column(name = "USER_ID", nullable = false, length = 100, unique = true)
    private String userId;

    @Column(name = "REFRESH_TOKEN", nullable = false)
    private String refreshToken;

    @Builder
    public RefreshToken(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}