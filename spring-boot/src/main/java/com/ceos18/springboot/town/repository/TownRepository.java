package com.ceos18.springboot.town.repository;

import com.ceos18.springboot.member.domain.User;
import com.ceos18.springboot.town.domain.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownRepository extends JpaRepository<Town, Long> {
}
