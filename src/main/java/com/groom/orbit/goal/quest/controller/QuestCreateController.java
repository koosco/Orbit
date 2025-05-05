package com.groom.orbit.goal.quest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.quest.application.QuestCreateService;
import com.groom.orbit.goal.quest.application.dto.request.CreateQuestRequestDto;
import com.groom.orbit.goal.quest.application.dto.response.CreateQuestResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quest")
public class QuestCreateController {

  private final QuestCreateService questCreateService;

  @PostMapping
  public ResponseDto<CreateQuestResponse> createQuest(
      @AuthMember Long memberId, @RequestBody CreateQuestRequestDto dto) {
    return ResponseDto.created(questCreateService.createQuest(memberId, dto));
  }
}
