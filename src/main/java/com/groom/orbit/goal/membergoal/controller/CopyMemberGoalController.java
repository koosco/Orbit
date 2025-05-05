package com.groom.orbit.goal.membergoal.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.goal.application.dto.response.GetMemberGoalResponseDto;
import com.groom.orbit.goal.membergoal.application.MemberGoalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class CopyMemberGoalController {

  private final MemberGoalService memberGoalService;

  @PostMapping("/copy/{member_goal_id}")
  public ResponseDto<GetMemberGoalResponseDto> copyMemberGoal(
      @AuthMember Long memberId, @PathVariable("member_goal_id") Long otherMemberGoalId) {
    return ResponseDto.ok(memberGoalService.createOtherGoal(memberId, otherMemberGoalId));
  }
}
