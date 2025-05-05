package com.groom.orbit.infra.ai.application.dto;

import lombok.Builder;

@Builder
public record UpdateVectorQuestDto(Long memberId, String quest, String newQuest) {}
