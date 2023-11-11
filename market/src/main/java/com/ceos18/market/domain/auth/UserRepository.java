package com.ceos18.market.domain.auth;

import com.ceos18.market.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserNo(String userNo);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
