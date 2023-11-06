package com.daagn.clonestudy.post.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

  Optional<Post> findTopByOrderByIdDesc();

  @Query("select p, i from Post p left join fetch p.writer left join PostImage i on p.id = i.post.id and i.isThumbnail = true where p.id < :lastId order by p.id desc, i.id asc")
  Page<Object[]> findAllWithImage(@Param("lastId") Long lastId, Pageable pageable);
}
