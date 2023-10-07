package com.daagn.clonestudy.post.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
  Optional<PostImage> findFirstByPost(Post post);
}
