package com.ceos18.springboot.repository;

import com.ceos18.springboot.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
