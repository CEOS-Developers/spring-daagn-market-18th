package com.ceos.daangnmarket.domain.product.entity;

import com.ceos.daangnmarket.common.BaseEntity;
import com.ceos.daangnmarket.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "price_offer")
public class PriceOffer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "offerer_id")
  private Account account;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  private Product product;

  private Integer offeredPrice;

  private Boolean acceptOrNot;

}
