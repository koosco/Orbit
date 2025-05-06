package com.groom.orbit.goal.goal.application.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.groom.orbit.goal.goal.repository.entity.GoalCategory;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.goal.quest.application.dto.response.GetQuestResponseDto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetMemberGoalResponseDto(
    Long memberGoalId,
    String goalTitle,
    GoalCategory category,
    Boolean isComplete,
    Integer sequence,
    Boolean isResume,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate completedDate,
    List<GetQuestResponseDto> quests) {

  public static GetMemberGoalResponseDto from(MemberGoal memberGoal) {
    return GetMemberGoalResponseDto.builder()
        .memberGoalId(memberGoal.getMemberGoalId())
        .goalTitle(memberGoal.getTitle())
        .category(memberGoal.getGoal().getCategory())
        .isComplete(memberGoal.getIsComplete())
        .sequence(memberGoal.getSequence())
        .isResume(memberGoal.getIsResume())
        .startDate(memberGoal.getCreatedAt().toLocalDate())
        .completedDate(memberGoal.getCreatedAt().toLocalDate())
        .quests(memberGoal.getQuests().stream().map(GetQuestResponseDto::from).toList())
        .build();
  }
}
