package com.groom.orbit.goal.membergoal.controller;

import org.springframework.web.bind.annotation.*;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.goal.application.dto.request.MemberGoalRequestDto;
import com.groom.orbit.goal.membergoal.application.UpdateMemberGoalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class UpdateMemberGoalController {

  private final UpdateMemberGoalService updateMemberGoalService;

  @PatchMapping("/{member_goal_id}")
  public ResponseDto<CommonSuccessDto> updateMemberGoal(
      @AuthMember Long memberId,
      @PathVariable("member_goal_id") Long memberGoalId,
      @RequestBody MemberGoalRequestDto dto) {
    return ResponseDto.ok(updateMemberGoalService.updateMemberGoal(memberId, memberGoalId, dto));
  }

  @PatchMapping("/complete/{member_goal_id}")
  public ResponseDto<CommonSuccessDto> updateMemberGoalIsComplete(
      @AuthMember Long memberId, @PathVariable("member_goal_id") Long memberGoalId) {
    return ResponseDto.ok(
        updateMemberGoalService.updateMemberGoalIsComplete(memberId, memberGoalId));
  }
}
