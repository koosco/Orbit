package com.groom.orbit.resume.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.resume.application.ResumeDeleteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeDeleteController {

  private final ResumeDeleteService resumeDeleteService;

  @DeleteMapping("/{resumeId}")
  public ResponseDto<CommonSuccessDto> deleteResume(
      @AuthMember Long memberId, @PathVariable Long resumeId) {
    return ResponseDto.ok(resumeDeleteService.deleteResume(memberId, resumeId));
  }
}
