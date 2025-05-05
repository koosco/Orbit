package com.groom.orbit.goal.membergoal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.goal.application.dto.request.UpdateMemberGoalSequenceRequestDto;
import com.groom.orbit.goal.membergoal.application.UpdateMemberGoalSequenceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class UpdateMemberGoalSequenceController {

  private final UpdateMemberGoalSequenceService updateMemberGoalSequenceService;

  @PatchMapping
  public ResponseDto<CommonSuccessDto> updateMemberGoalSequence(
      @AuthMember Long memberId,
      @RequestBody List<UpdateMemberGoalSequenceRequestDto> requestDtoList) {
    return ResponseDto.ok(
        updateMemberGoalSequenceService.updateMemberGoalSequence(memberId, requestDtoList));
  }
}
