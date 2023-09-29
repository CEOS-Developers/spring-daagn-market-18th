package com.daagn.clonestudy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nickname;

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
  public Member(String nickname, String town, String icon){
    this.nickname = nickname;
    this.town = town;
    this.icon = icon;
    this.temperature = 36.5;
    this.redeal = 0;
    this.responseRate = 0;
  }

}
