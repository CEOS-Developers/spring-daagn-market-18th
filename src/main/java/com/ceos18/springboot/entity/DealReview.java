package com.ceos18.springboot.entity;

import com.ceos18.springboot.entity.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "deal_reviews")
public class DealReview extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // PK

	@ManyToOne
	@JoinColumn(name = "completed_deal_id")
	private CompletedDeal completedDeal; // FK

	@ManyToOne
	@JoinColumn(name = "author_id", nullable = false)
	private Member author;

	@Column(name = "member_role", nullable = false)
	private String memberRole;

	@Column(name = "preference", nullable = false)
	private String preference;

	@Column(name = "messages")
	private String messages;

}
