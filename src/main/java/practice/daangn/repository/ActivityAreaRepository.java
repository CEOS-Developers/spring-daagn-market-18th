package practice.daangn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.daangn.domain.ActivityArea;
@Repository
public interface ActivityAreaRepository extends JpaRepository<ActivityArea, Long> {
}
