package com.ceos18.springboot.member.domain;

import com.ceos18.springboot.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private Double temperature;

    @Column(nullable = false)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    @Column(nullable = false)
    private Boolean activated;

    @Builder
    public Member(String password, String nickname, String phone, String email, String imageUrl) {
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.temperature = 36.5;
        this.email = email;
        this.imageUrl = imageUrl;
        this.roles = new ArrayList<>() {{
            add(Role.USER);
        }};
        this.activated = true;
    }

    public void encodePassword(String password) {
        this.password = password;
    }

    public void updateActivatedFalse() {
        this.activated = false;
    }
}
