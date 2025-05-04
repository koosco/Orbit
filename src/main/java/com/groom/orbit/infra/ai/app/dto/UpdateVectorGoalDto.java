package com.groom.orbit.infra.ai.app.dto;

import lombok.Builder;

@Builder
public record UpdateVectorGoalDto(Long memberId, String goal, String newGoal) {}
