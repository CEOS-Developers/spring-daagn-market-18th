package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "items")
public class Item extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private String id; // PK

	@OneToOne
	@Column(name = "category_id")
	private Category category; // FK

	@ManyToOne
	@Column(name = "seller_id")
	private Member seller; // FK

	private String title;

	private String dealType;

	private String description;

	private String dealPlace;

	private String price;

	private Boolean isOffer;

	private String status;

	private String likedCount;

	private String viewCount;

	private String pullupAt;

}
