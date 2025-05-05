package com.groom.orbit.goal.goal.application.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.groom.orbit.goal.goal.repository.entity.GoalCategory;
import com.groom.orbit.goal.quest.application.dto.response.GetQuestResponseDto;

public record GetMemberGoalResponseDto(
    Long memberGoalId,
    String goalTitle,
    GoalCategory category,
    Boolean isComplete,
    Integer sequence,
    Boolean isResume,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate completedDate,
    List<GetQuestResponseDto> quests) {}
