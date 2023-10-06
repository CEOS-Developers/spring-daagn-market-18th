package com.daagn.clonestudy.post.domain;

import com.daagn.clonestudy.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
