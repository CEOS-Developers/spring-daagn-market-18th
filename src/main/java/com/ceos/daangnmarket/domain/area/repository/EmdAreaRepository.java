package com.ceos.daangnmarket.domain.area.repository;

import com.ceos.daangnmarket.domain.area.entity.EmdArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmdAreaRepository extends JpaRepository<EmdArea, Long> {
}
