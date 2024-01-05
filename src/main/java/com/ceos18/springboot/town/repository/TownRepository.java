package com.ceos18.springboot.town.repository;

import com.ceos18.springboot.town.domain.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
}
