package com.carrot.clonecoding.achievement.domain.repository;

import com.carrot.clonecoding.achievement.domain.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
}
