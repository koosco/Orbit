package com.groom.orbit.goal.quest.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.goal.quest.repository.QuestRepository;
import com.groom.orbit.goal.quest.repository.entity.Quest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestDeleteService {

  private final QuestQueryService questQueryService;
  private final QuestRepository questRepository;

  public CommonSuccessDto deleteQuest(Long memberId, Long questId) {
    Quest quest = questQueryService.findQuest(questId);
    quest.validateMember(memberId);

    questRepository.delete(quest);

    return CommonSuccessDto.fromEntity(true);
  }
}
