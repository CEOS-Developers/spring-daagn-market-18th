package com.ceos18.springboot.chat.repository;

import com.ceos18.springboot.chat.domain.ChatMsg;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatMsgRepository extends JpaRepository<ChatMsg, Long> {
}
