package com.groom.orbit.goal.quest.app.dto.request;

import java.time.LocalDate;

public record CreateQuestRequestDto(Long goalId, String title, LocalDate deadline) {}
