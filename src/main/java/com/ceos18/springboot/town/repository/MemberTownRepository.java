package com.ceos18.springboot.town.repository;

import com.ceos18.springboot.member.domain.Member;
import com.ceos18.springboot.town.domain.MemberTown;
import com.ceos18.springboot.town.domain.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberTownRepository extends JpaRepository<MemberTown, Long> {

    Optional<MemberTown> findByMemberAndTown(Member member, Town town);
}
