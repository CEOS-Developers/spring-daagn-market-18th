package com.ceos18.springboot.member.repository;

import com.ceos18.springboot.chat.domain.ChatMsg;
import com.ceos18.springboot.member.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
