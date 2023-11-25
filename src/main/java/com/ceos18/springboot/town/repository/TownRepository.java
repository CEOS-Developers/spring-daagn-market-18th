package com.ceos18.springboot.town.repository;

import com.ceos18.springboot.town.domain.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

    Boolean existsByName(String name);

    Optional<Town> findByName(String name);
}
