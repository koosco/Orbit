package com.groom.orbit.resume.controller;

import org.springframework.web.bind.annotation.*;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.resume.application.ResumeCreateService;
import com.groom.orbit.resume.application.dto.ResumeRequestDto;
import com.groom.orbit.resume.application.dto.ResumeResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeCreateController {

  private final ResumeCreateService resumeCreateService;

  @PostMapping()
  public ResponseDto<CommonSuccessDto> createResume(
      @AuthMember Long memberId, @RequestBody ResumeRequestDto requestDto) {
    return ResponseDto.ok(resumeCreateService.createResume(memberId, requestDto));
  }

  @PostMapping("/{member_goal_id}")
  public ResponseDto<ResumeResponseDto> createResumeFromMemberGoal(
      @AuthMember Long memberId,
      @PathVariable(name = "member_goal_id") Long memberGoalId,
      @RequestBody ResumeRequestDto requestDto) {
    return ResponseDto.ok(
        resumeCreateService.createResumeFromMemberGoal(memberId, memberGoalId, requestDto));
  }
}
