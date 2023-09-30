package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private String id; // PK

	private String name;

}
