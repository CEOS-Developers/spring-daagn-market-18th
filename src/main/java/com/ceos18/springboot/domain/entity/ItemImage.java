package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "item_images")
public class ItemImage extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private String id; // PK

	@ManyToOne
	@Column(name = "items_id")
	private Item item; // FK

	private String url;

}
