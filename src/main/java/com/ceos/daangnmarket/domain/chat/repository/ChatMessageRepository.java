package com.ceos.daangnmarket.domain.chat.repository;

import com.ceos.daangnmarket.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<User, Long> {
  // 채팅방 별 채팅 메시지 목록 조회
}
