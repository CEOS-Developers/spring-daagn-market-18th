package com.ceos18.springboot.post.repository;

import com.ceos18.springboot.member.domain.User;
import com.ceos18.springboot.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
