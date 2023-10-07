package com.ceos.daangnmarket.domain.product.entity;

import com.ceos.daangnmarket.common.BaseEntity;
import com.ceos.daangnmarket.domain.area.entity.EmdArea;
import com.ceos.daangnmarket.domain.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "seller_id")
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "selling_area_id")
  private EmdArea emdArea;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id")
  private Category category;

  private String title;

  private String status;

  private Integer sellPrice;

  private Integer viewCount;

  private String description;

  private LocalDateTime refreshedAt;

  @Builder
  public Product(Long id, User user, EmdArea emdArea, Category category, String title, String status, Integer sellPrice, Integer viewCount, String description, LocalDateTime refreshedAt) {
    this.id = id;
    this.user = user;
    this.emdArea = emdArea;
    this.category = category;
    this.title = title;
    this.status = status;
    this.sellPrice = sellPrice;
    this.viewCount = viewCount;
    this.description = description;
    this.refreshedAt = refreshedAt;
  }

}
