package com.ceos18.springboot.chat.repository;

import com.ceos18.springboot.chat.domain.ChatMsg;
import com.ceos18.springboot.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
