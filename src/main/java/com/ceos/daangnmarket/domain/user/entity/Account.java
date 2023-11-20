package com.ceos.daangnmarket.domain.user.entity;

import com.ceos.daangnmarket.common.BaseEntity;
import jakarta.persistence.*;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class Account extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nickname;

  private String password;

  private String phoneNumber;

  private Double ratingScore;

  private boolean activated;

  @ManyToMany
  @JoinTable(
    name = "account_authority",
    joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
  private Set<Authority> authorities;

  @Builder
  public Account(String nickname, String password, String phoneNumber, boolean activated, Set<Authority> authorities){
    this.nickname = nickname;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.ratingScore = Double.valueOf(36);
    this.activated = activated;
    this.authorities = authorities;
  }
}
