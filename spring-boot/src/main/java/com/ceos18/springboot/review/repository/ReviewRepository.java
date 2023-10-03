package com.ceos18.springboot.review.repository;

import com.ceos18.springboot.member.domain.User;
import com.ceos18.springboot.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
