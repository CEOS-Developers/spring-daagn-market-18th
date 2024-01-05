package com.ceos18.springboot.post.repository;

import com.ceos18.springboot.post.domain.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
