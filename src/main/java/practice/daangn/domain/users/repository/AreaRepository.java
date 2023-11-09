package practice.daangn.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.daangn.domain.users.entity.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

}
