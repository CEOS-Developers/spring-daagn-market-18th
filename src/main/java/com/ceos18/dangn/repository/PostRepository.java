package com.ceos18.dangn.repository;

import com.ceos18.dangn.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByIsDel(boolean isDel, Pageable pageable);

    @Modifying
    @Query("UPDATE Post p SET p.view = p.view + 1 WHERE p.id = :postId")
    void updateView(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE Post p SET p.isDel = true WHERE p.id = :postId")
    void deletePost(@Param("postId") Long postId);
}
