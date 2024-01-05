package com.ceos.daangnmarket.domain.review.repository;

import com.ceos.daangnmarket.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  // 사용자 별 리뷰 조회
}
