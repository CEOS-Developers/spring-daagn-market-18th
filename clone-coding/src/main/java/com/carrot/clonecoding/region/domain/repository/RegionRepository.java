package com.carrot.clonecoding.region.domain.repository;

import com.carrot.clonecoding.region.domain.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>
{
}
