package com.groom.orbit.goal.goal.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.goal.application.GoalSearchService;
import com.groom.orbit.goal.goal.application.dto.response.GoalSearchDetailResponseDto;
import com.groom.orbit.goal.goal.application.dto.response.GoalSearchResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal/search")
public class GoalSearchController {

  private final GoalSearchService goalSearchService;

  @GetMapping("/{goal_id}")
  public ResponseDto<GoalSearchDetailResponseDto> getSearchDetail(
      @PathVariable("goal_id") Long goalId) {
    return ResponseDto.ok(goalSearchService.searchGoalByGoalId(goalId));
  }

  @GetMapping
  public ResponseDto<Page<GoalSearchResponseDto>> searchGoals(
      @AuthMember Long memberId,
      @RequestParam(value = "category", required = false) String category,
      @RequestParam(value = "jobIds", required = false) List<Long> jobIds,
      @PageableDefault(size = 10) Pageable pageable) {
    return ResponseDto.ok(goalSearchService.searchGoals(memberId, category, jobIds, pageable));
  }
}
