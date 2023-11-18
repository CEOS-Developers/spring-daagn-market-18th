package com.carrot.clonecoding.auth.domain.repository;

import com.carrot.clonecoding.auth.domain.model.Authority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthorityName(String authorityName);
}
