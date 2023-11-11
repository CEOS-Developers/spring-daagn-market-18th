package com.ceos18.springboot.repository;

import com.ceos18.springboot.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByAccount(String account);
}
