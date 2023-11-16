package com.ceos18.springboot.entity;

import com.ceos18.springboot.entity.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "post_images")
public class PostImage extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // PK

	@ManyToOne
	@JoinColumn(name = "post_id",nullable = false)
	private Post post;

	@Column(name = "url")
	private String url;
}
