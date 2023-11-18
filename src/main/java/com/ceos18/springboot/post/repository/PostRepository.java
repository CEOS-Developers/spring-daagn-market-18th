package com.ceos18.springboot.post.repository;

import com.ceos18.springboot.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndActivated(Long id, Boolean activated);
}
