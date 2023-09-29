package com.ceos18.springboot.town.repository;

import com.ceos18.springboot.town.domain.MemberTown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTownRepository extends JpaRepository<MemberTown, Long> {
}
