package com.groom.orbit.schedule.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.schedule.application.ScheduleCommandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleDeleteController {

  private final ScheduleCommandService scheduleCommandService;

  @DeleteMapping("/{scheduleId}")
  public ResponseDto<CommonSuccessDto> deleteSchedule(
      @AuthMember Long memberId, @PathVariable Long scheduleId) {
    return ResponseDto.ok(scheduleCommandService.deleteSchedule(memberId, scheduleId));
  }
}
