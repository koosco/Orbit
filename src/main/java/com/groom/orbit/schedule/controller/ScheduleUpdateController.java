package com.groom.orbit.schedule.controller;

import org.springframework.web.bind.annotation.*;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.schedule.application.ScheduleCommandService;
import com.groom.orbit.schedule.application.dto.ScheduleRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleUpdateController {

  private final ScheduleCommandService scheduleCommandService;

  @PatchMapping("/{scheduleId}")
  public ResponseDto<CommonSuccessDto> updateSchedule(
      @AuthMember Long memberId,
      @PathVariable Long scheduleId,
      @RequestBody ScheduleRequestDto scheduleRequestDto) {
    return ResponseDto.ok(
        scheduleCommandService.updateSchedule(memberId, scheduleId, scheduleRequestDto));
  }
}
