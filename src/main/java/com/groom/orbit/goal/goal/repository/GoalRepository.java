package com.groom.orbit.goal.goal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.goal.goal.repository.entity.Goal;
import com.groom.orbit.goal.goal.repository.entity.GoalCategory;

public interface GoalRepository extends JpaRepository<Goal, Long> {

  Optional<Goal> findByTitleAndCategory(String title, GoalCategory category);
}
