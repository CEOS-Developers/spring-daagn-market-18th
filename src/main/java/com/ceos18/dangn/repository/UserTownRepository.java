package com.ceos18.dangn.repository;

import com.ceos18.dangn.domain.Town;
import com.ceos18.dangn.domain.User;
import com.ceos18.dangn.domain.UserTown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTownRepository extends JpaRepository<UserTown, Long> {
    List<UserTown> findByUser(User user);

    Optional<UserTown> findByTown(Town town);

    List<UserTown> findByUserAndTown(User user, Town town);
}
