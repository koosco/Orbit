package com.groom.orbit.goal.quest.application.dto.request;

import java.time.LocalDate;

public record CreateQuestRequestDto(Long goalId, String title, LocalDate deadline) {}
