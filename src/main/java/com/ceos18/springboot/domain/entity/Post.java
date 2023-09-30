package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import com.ceos18.springboot.domain.entity.enums.DealType;
import com.ceos18.springboot.domain.entity.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "posts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // PK

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private Member seller;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "deal_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private DealType dealType;

	@Column(name = "description")
	private String description;

	@Column(name = "deal_place")
	private String dealPlace;

	@Column(name = "price")
	private String price;

	@Column(name = "is_price_offer", nullable = false)
	private Boolean isPriceOffer;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private PostStatus status;

	@Column(name = "liked_count", nullable = false)
	private int likedCount;

	@Column(name = "view_count", nullable = false)
	private int viewCount;

	@Column(name = "pullup_at")
	private LocalDateTime pullupAt;

}
