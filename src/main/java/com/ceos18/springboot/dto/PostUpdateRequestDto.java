package com.ceos18.springboot.dto;

import com.ceos18.springboot.domain.entity.enums.DealType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
	private String title;
	private Long categoryId;
	private DealType dealType;
	private String description;
	private String dealPlace;
	private BigDecimal price;
	private Boolean isPriceOffer;
}
