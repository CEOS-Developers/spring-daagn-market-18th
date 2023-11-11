package com.ceos18.springboot.entity;

import com.ceos18.springboot.entity.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "completed_deals")
public class CompletedDeal extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // PK

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne
	@JoinColumn(name = "buyer_id", nullable = false)
	private Member buyer;

}
