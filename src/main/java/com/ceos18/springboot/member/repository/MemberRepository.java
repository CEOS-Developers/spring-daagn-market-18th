package com.ceos18.springboot.member.repository;

import com.ceos18.springboot.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdAndActivated(Long id, Boolean activated);

    List<Member> findAllByActivated(Boolean activated);
}
