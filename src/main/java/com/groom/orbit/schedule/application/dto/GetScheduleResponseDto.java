package com.groom.orbit.schedule.application.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.groom.orbit.schedule.repository.jpa.entity.Schedule;

public record GetScheduleResponseDto(
    Long scheduleId,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
    String content) {

  public static GetScheduleResponseDto fromSchedule(Schedule schedule) {
    return new GetScheduleResponseDto(
        schedule.getScheduleId(),
        schedule.getStartDate(),
        schedule.getEndDate(),
        schedule.getContent());
  }
}
