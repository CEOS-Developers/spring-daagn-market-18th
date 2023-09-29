package com.daagn.clonestudy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
public class Purchase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Boolean confirmed;

  @JoinColumn(nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member buyer;

  @JoinColumn(nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @Builder
  public Purchase(Member buyer, Post post){
    this.confirmed = false;
    this. buyer = buyer;
    this.post = post;
  }

}
