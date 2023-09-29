package com.daagn.clonestudy;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chatting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @JoinColumn(nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member sender;

  @JoinColumn(nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member receiver;

}
