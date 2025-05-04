package com.groom.orbit.infra.ai.application.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record CreateVectorDto(
    Long memberId,
    String memberName,
    List<String> interestJobs,
    List<String> goals,
    List<String> quests) {}
