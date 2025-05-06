package com.groom.orbit.goal.goal.application.dto.response;

import com.groom.orbit.goal.goal.repository.entity.MemberGoal;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GoalSearchResponseDto(Long goalId, String title, String category, int count) {

  public static GoalSearchResponseDto from(MemberGoal memberGoal) {
    return GoalSearchResponseDto.builder()
        .goalId(memberGoal.getGoal().getGoalId())
        .title(memberGoal.getGoal().getTitle())
        .category(memberGoal.getGoal().getCategory().name())
        .count(memberGoal.getGoal().getCount())
        .build();
  }
}
