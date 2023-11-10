package com.carrot.clonecoding.user.domain.repository;

import com.carrot.clonecoding.user.domain.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByPhonenum(String phonenum);
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findByNickName(String nickName);
}

