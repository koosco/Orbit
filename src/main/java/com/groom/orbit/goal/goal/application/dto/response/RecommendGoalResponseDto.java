package com.groom.orbit.goal.goal.application.dto.response;

import java.util.List;

public record RecommendGoalResponseDto(String title, String category, List<String> descriptions) {}
