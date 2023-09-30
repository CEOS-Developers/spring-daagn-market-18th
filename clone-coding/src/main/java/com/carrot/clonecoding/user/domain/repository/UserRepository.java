package com.carrot.clonecoding.user.domain.repository;

import com.carrot.clonecoding.Person;
import com.carrot.clonecoding.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
}

