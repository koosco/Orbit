package com.groom.orbit.infra.ai.application.dto;

import lombok.Builder;

@Builder
public record UpdateVectorGoalDto(Long memberId, String goal, String newGoal) {}
