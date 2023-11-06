package com.ceos.daangnmarket.domain.product.dto;

import lombok.Data;

@Data
public class PostProductRequest {
  private Long areaId;
  private Long categoryId;

  private String title;
  private String status;
  private Integer sellPrice;
  private String description;
}
