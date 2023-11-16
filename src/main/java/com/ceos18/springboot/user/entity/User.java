package com.ceos18.springboot.user.entity;

import com.ceos18.springboot.common.entity.BaseTimeEntity;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.math.BigDecimal;
import jakarta.persistence.*;


@Entity
@Getter
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "temperature", nullable = false, columnDefinition = "DECIMAL(4, 2) DEFAULT 36.5")
    private BigDecimal temperature;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String email, String nickname, String phone, String password,
                String imgUrl, BigDecimal temperature) {
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
        this.password = password;
        this.imgUrl = imgUrl;
        this.temperature = temperature;
        this.role = Role.ROLE_USER;
    }

    public String getImgUrl() {
        return imgUrl;
    }

}
