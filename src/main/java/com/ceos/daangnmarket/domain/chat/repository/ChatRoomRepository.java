package com.ceos.daangnmarket.domain.chat.repository;

import com.ceos.daangnmarket.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<User, Long> {
  // 사용자 별 채팅 목록 조회
}
