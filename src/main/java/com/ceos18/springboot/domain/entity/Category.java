package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // PK

	@Column(name = "name")
	private String name;

}
