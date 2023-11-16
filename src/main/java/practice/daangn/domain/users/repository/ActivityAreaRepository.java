package practice.daangn.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.daangn.domain.users.entity.ActivityArea;
@Repository
public interface ActivityAreaRepository extends JpaRepository<ActivityArea, Long> {
}
