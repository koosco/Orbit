package com.groom.orbit.goal.membergoal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.goal.application.dto.response.GetMemberGoalResponseDto;
import com.groom.orbit.goal.membergoal.application.MemberGoalQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class MemberGoalQueryController {

  private final MemberGoalQueryService memberGoalQueryService;

  @GetMapping
  public ResponseDto<List<GetMemberGoalResponseDto>> getGoals(
      @AuthMember Long memberId,
      @RequestParam(value = "is_complete", required = false) Boolean isComplete) {
    return ResponseDto.ok(memberGoalQueryService.findMemberGoals(memberId, isComplete));
  }

  @GetMapping("/{member_goal_id}")
  public ResponseDto<GetMemberGoalResponseDto> getGoal(
      @PathVariable("member_goal_id") Long memberGoalId) {
    return ResponseDto.ok(memberGoalQueryService.findGoal(memberGoalId));
  }
}
