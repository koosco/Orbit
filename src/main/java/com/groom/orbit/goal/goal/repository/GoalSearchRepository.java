package com.groom.orbit.goal.goal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.goal.goal.repository.entity.Goal;

public interface GoalSearchRepository extends JpaRepository<Goal, Long> {}
