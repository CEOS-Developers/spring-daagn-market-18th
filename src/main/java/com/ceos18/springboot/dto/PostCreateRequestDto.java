package com.ceos18.springboot.dto;

import com.ceos18.springboot.domain.entity.Category;
import com.ceos18.springboot.domain.entity.Member;
import com.ceos18.springboot.domain.entity.Post;
import com.ceos18.springboot.domain.entity.enums.DealType;
import com.ceos18.springboot.domain.entity.enums.PostStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PostCreateRequestDto {


	private String title;
	private Long categoryId;
	private DealType dealType;
	private String description;
	private String dealPlace;
	private BigDecimal price;
	private Boolean isPriceOffer;

}
