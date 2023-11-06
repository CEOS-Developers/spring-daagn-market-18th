package com.ceos18.springboot.vo;

import com.ceos18.springboot.domain.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryVo {
	private Long id;
	private String name;

	public static CategoryVo fromEntity(Category category) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setId(category.getId());
		categoryVo.setName(category.getName());
		return categoryVo;
	}

	public Category toEntity() {
		Category category = new Category();
		category.setId(this.id);
		category.setName(this.name);
		return category;
	}
}
