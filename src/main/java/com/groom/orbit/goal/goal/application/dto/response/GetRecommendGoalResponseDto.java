package com.groom.orbit.goal.goal.application.dto.response;

import java.util.List;

public record GetRecommendGoalResponseDto(
    Long goalId, String title, String category, List<String> descriptions) {}
