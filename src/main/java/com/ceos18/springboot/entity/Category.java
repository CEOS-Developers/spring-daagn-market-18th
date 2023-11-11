package com.ceos18.springboot.entity;

import com.ceos18.springboot.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // PK

	@Column(name = "name")
	private String name;

}
