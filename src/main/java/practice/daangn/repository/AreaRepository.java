package practice.daangn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.daangn.domain.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

}
