package com.groom.orbit.goal.membergoal.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.goal.application.dto.request.MemberGoalRequestDto;
import com.groom.orbit.goal.goal.application.dto.response.GetMemberGoalResponseDto;
import com.groom.orbit.goal.membergoal.application.CreateMemberGoalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class CreateMemberGoalController {

  private final CreateMemberGoalService createMemberGoalService;

  @PostMapping
  public ResponseDto<GetMemberGoalResponseDto> createMemberGoal(
      @AuthMember Long memberId, @RequestBody MemberGoalRequestDto dto) {
    return ResponseDto.created(createMemberGoalService.createGoal(memberId, dto));
  }
}
