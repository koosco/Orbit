package com.groom.orbit.goal.quest.app.dto.request;

import java.time.LocalDate;

public record UpdateQuestRequestDto(String title, Boolean isComplete, LocalDate deadline) {}
