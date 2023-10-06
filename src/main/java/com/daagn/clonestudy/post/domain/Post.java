package com.daagn.clonestudy.post.domain;

import com.daagn.clonestudy.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Getter
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private Boolean isAuction;

  @Column
  private String description;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PostStatus status;

  @JoinColumn(nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Member writer;

  @Builder
  public Post(String title, Integer price, Boolean isAuction, String description, String address, Member writer){
    this.title = title;
    this.price = price;
    this.isAuction = isAuction;
    this.description = description;
    this.address = address;
    this.writer = writer;
    this.status = PostStatus.SELLING;
  }

}
