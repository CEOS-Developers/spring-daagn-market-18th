package com.ceos18.springboot.repository;

import com.ceos18.springboot.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
