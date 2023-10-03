package com.ceos18.springboot.ChatContent.Repository;

import com.ceos18.springboot.ChatContent.domain.ChatContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatContentRepository extends JpaRepository<ChatContent, Long> {
}
