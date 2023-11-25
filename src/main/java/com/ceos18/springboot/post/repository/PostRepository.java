package com.ceos18.springboot.post.repository;

import com.ceos18.springboot.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByDeletedAtIsNull();
    Optional<Post> findByIdAndDeletedAtIsNull(Long id);
}
