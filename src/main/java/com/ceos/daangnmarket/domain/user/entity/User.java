package com.ceos.daangnmarket.domain.user.entity;

import com.ceos.daangnmarket.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nickname;

  private String password;

  private String phoneNumber;

  private Double ratingScore;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Builder
  public User(String nickname, String password, String phoneNumber) {
    this.nickname = nickname;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.ratingScore = Double.valueOf(36);
    this.role = Role.USER;
  }

}
