package com.ceos18.springboot.chatting.repository;

import com.ceos18.springboot.chatting.entity.ChatMessage;
import com.ceos18.springboot.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> getChatMessagesByChatRoom(ChatRoom chatRoom);
}
