package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "deal_reviews")
public class DealReview extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private String id; // PK

	@ManyToOne
	@Column(name = "items_id")
	private Item item; // PK

	@ManyToOne
	@Column(name = "author_id")
	private Member author; // FK

	private String preference;
	private String selections;
	private String messages;

}
