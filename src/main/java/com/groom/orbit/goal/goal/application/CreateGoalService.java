package com.groom.orbit.goal.goal.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.goal.goal.repository.GoalRepository;
import com.groom.orbit.goal.goal.repository.entity.Goal;
import com.groom.orbit.goal.goal.repository.entity.GoalCategory;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateGoalService {

  private final GoalRepository goalRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Goal createGoal(String title, String category) {
    Goal goal = Goal.create(title, category);

    return goalRepository.save(goal);
  }

  public Goal upsert(String title, String category) {
    Optional<Goal> optionalGoal =
        goalRepository.findByTitleAndCategory(title, GoalCategory.from(category));
    return optionalGoal.orElseGet(() -> createGoal(title, category));
  }
}
