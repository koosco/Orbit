package com.groom.orbit.schedule.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.goal.quest.dao.QuestRepository;
import com.groom.orbit.schedule.application.dto.GetCalendarResponseDto;
import com.groom.orbit.schedule.application.dto.GetQuestResponseDto;
import com.groom.orbit.schedule.application.dto.GetScheduleResponseDto;
import com.groom.orbit.schedule.repository.jpa.ScheduleRepository;
import com.groom.orbit.schedule.repository.jpa.entity.Schedule;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleQueryService {

  private final ScheduleRepository scheduleRepository;
  private final QuestRepository questRepository;

  public Schedule findSchedule(Long scheduleId) {
    return scheduleRepository
        .findById(scheduleId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SCHEDULE));
  }

  public GetCalendarResponseDto getCalendar(Long memberId, Integer month, Integer year) {
    List<GetScheduleResponseDto> scheduleResponseDtoList =
        scheduleRepository.findAllByMonthAndMemberId(memberId, month, year).stream()
            .map(GetScheduleResponseDto::fromSchedule)
            .toList();

    List<GetQuestResponseDto> questResponseDtoList =
        questRepository.findAllByMonthAndMemberId(memberId, month, year).stream()
            .map(GetQuestResponseDto::fromQuest)
            .toList();

    return GetCalendarResponseDto.fromCalendarList(questResponseDtoList, scheduleResponseDtoList);
  }
}
