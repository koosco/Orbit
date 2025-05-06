package com.groom.orbit.goal.membergoal.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.membergoal.application.DeleteMemberGoalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class DeleteMemberGoalController {

  private final DeleteMemberGoalService deleteMemberGoalService;

  @DeleteMapping("/{member_goal_id}")
  public ResponseDto<CommonSuccessDto> deleteMemberGoal(
      @AuthMember Long memberId, @PathVariable("member_goal_id") Long memberGoalId) {
    return ResponseDto.ok(deleteMemberGoalService.deleteMemberGoal(memberId, memberGoalId));
  }
}
