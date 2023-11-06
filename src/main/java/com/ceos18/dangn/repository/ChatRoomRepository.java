package com.ceos18.dangn.repository;

import com.ceos18.dangn.domain.ChatRoom;
import com.ceos18.dangn.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT COALESCE(COUNT(cr.id), 0) FROM ChatRoom cr " +
            "WHERE cr.post = :post")
    int getTotalChatRoom(@Param("post") Post post);
}
