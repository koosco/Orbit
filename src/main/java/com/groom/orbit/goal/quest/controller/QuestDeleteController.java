package com.groom.orbit.goal.quest.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.quest.application.QuestDeleteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quest")
public class QuestDeleteController {

  private final QuestDeleteService questDeleteService;

  @DeleteMapping("/{quest_id}")
  public ResponseDto<CommonSuccessDto> deleteQuest(
      @AuthMember Long memberId, @PathVariable("quest_id") Long questId) {
    return ResponseDto.ok(questDeleteService.deleteQuest(memberId, questId));
  }
}
