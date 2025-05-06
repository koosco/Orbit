package com.groom.orbit.infra.ai.application.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record CreateVectorDto(
    Long memberId,
    String memberName,
    List<String> interestJobs,
    List<String> goals,
    List<String> quests) {

  public static CreateVectorDto from(
      Long memberId, List<String> goalTitles, List<String> questTitles) {
    return CreateVectorDto.builder()
        .memberId(memberId)
        .goals(goalTitles)
        .quests(questTitles)
        .build();
  }
}
