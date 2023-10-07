package com.daagn.clonestudy.post.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
  Page<Post> findAllByIdLessThanOrderByIdDesc(Long id, PageRequest pageRequest);
  Optional<Post> findTopByOrderByIdDesc();
}
