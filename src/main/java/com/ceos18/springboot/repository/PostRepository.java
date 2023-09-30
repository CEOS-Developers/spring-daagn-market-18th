package com.ceos18.springboot.repository;

import com.ceos18.springboot.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {


}