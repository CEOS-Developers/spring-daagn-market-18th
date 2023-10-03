package com.carrot.clonecoding.chat.domain.repository;


import com.carrot.clonecoding.chat.domain.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
}
