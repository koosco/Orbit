package com.groom.orbit.goal.goal.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.groom.orbit.goal.goal.repository.entity.Goal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalResolver {

  private final GoalQueryService goalQueryService;
  private final CreateGoalService createGoalService;

  public Goal findOrCreateGoal(String title, String category) {
    Optional<Goal> findGoal = goalQueryService.findGoalByTitleAndCategory(title, category);

    return findGoal.orElseGet(() -> createGoalService.createGoal(title, category));
  }
}
