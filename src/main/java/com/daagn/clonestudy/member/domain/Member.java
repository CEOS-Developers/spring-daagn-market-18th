package com.daagn.clonestudy.member.domain;

import com.daagn.clonestudy.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@NoArgsConstructor
public class Member extends BaseTimeEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String town;

  @Column
  private String icon;

  @Column(nullable = false)
  private Double temperature;

  @Column(nullable = false)
  private Integer redeal;

  @Column(nullable = false)
  private Integer responseRate;

  @Builder
  public Member(String nickname, String town, String icon, String phoneNumber){
    this.nickname = nickname;
    this.town = town;
    this.icon = icon;
    this.phoneNumber = phoneNumber;
    this.temperature = 36.5;
    this.redeal = 0;
    this.responseRate = 0;
  }

  @ElementCollection
  @Builder.Default
  private List<String> roles = new ArrayList<>();

  @Override
  public String getUsername() {
    return phoneNumber;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
