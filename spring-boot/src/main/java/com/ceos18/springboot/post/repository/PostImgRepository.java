package com.ceos18.springboot.post.repository;

import com.ceos18.springboot.member.domain.User;
import com.ceos18.springboot.post.domain.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImgRepository extends JpaRepository<PostImg, Long> {
}
