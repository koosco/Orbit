package com.groom.orbit.goal.quest.application.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.groom.orbit.goal.quest.repository.entity.Quest;

import lombok.Builder;

@Builder
public record GetQuestResponseDto(
    Long id,
    String title,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
    Boolean isComplete) {

  public static GetQuestResponseDto from(Quest quest) {
    return GetQuestResponseDto.builder()
        .id(quest.getQuestId())
        .title(quest.getTitle())
        .deadline(quest.getDeadline())
        .isComplete(quest.getIsComplete())
        .build();
  }
}
