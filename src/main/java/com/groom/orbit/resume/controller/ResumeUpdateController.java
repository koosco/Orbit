package com.groom.orbit.resume.controller;

import org.springframework.web.bind.annotation.*;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.resume.application.ResumeUpdateService;
import com.groom.orbit.resume.application.dto.ResumeRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeUpdateController {

  private final ResumeUpdateService resumeUpdateService;

  @PatchMapping("/{resumeId}")
  public ResponseDto<CommonSuccessDto> updateResume(
      @AuthMember Long memberId,
      @PathVariable Long resumeId,
      @RequestBody ResumeRequestDto requestDto) {
    return ResponseDto.ok(resumeUpdateService.updateResume(memberId, resumeId, requestDto));
  }
}
