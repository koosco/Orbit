package com.groom.orbit.goal.quest.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.goal.quest.application.dto.request.UpdateQuestRequestDto;
import com.groom.orbit.goal.quest.repository.entity.Quest;
import com.groom.orbit.infra.ai.application.VectorService;
import com.groom.orbit.infra.ai.application.dto.UpdateVectorQuestDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestUpdateService {

  private final QuestQueryService questQueryService;
  private final VectorService vectorService;

  public CommonSuccessDto updateQuest(Long memberId, Long questId, UpdateQuestRequestDto dto) {
    Quest quest = questQueryService.findQuest(questId);
    quest.validateMember(memberId);

    updateVector(memberId, dto, quest);
    quest.update(dto.title(), dto.isComplete(), dto.deadline());

    return new CommonSuccessDto(true);
  }

  private void updateVector(Long memberId, UpdateQuestRequestDto dto, Quest quest) {
    UpdateVectorQuestDto updateDto =
        UpdateVectorQuestDto.builder()
            .memberId(memberId)
            .quest(quest.getTitle())
            .newQuest(dto.title())
            .build();
    vectorService.updateQuest(updateDto);
  }
}
