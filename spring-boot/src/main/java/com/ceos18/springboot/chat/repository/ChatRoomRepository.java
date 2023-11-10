package com.ceos18.springboot.chat.repository;

import com.ceos18.springboot.chat.domain.ChatMsg;
import com.ceos18.springboot.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
