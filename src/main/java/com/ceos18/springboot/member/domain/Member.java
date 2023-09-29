package com.ceos18.springboot.member.domain;

import com.ceos18.springboot.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Float temperature;

    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private Boolean activated;

    @Builder
    public Member(String password, String nickname, String phone, Float temperature, String email, String imageUrl, Boolean activated) {
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.temperature = temperature;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = activated;
    }
}
