package com.groom.orbit.goal.quest.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.goal.membergoal.application.MemberGoalQueryService;
import com.groom.orbit.goal.quest.application.dto.request.CreateQuestRequestDto;
import com.groom.orbit.goal.quest.application.dto.response.CreateQuestResponse;
import com.groom.orbit.goal.quest.repository.QuestRepository;
import com.groom.orbit.goal.quest.repository.entity.Quest;
import com.groom.orbit.infra.ai.application.VectorService;
import com.groom.orbit.infra.ai.application.dto.CreateVectorDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestCreateService {

  private final MemberGoalQueryService memberGoalQueryService;
  private final QuestQueryService questQueryService;
  private final VectorService vectorService;

  private final QuestRepository questRepository;

  /** TODO join 최적화 */
  public CreateQuestResponse createQuest(Long memberId, CreateQuestRequestDto dto) {
    MemberGoal memberGoal = memberGoalQueryService.findMemberGoal(memberId, dto.goalId());
    memberGoal.validateMember(memberId);

    int newQuestSequence = questQueryService.getQuestCountsByGoalId(dto.goalId()) + 1;
    Quest quest = Quest.create(dto.title(), memberGoal, dto.deadline(), newQuestSequence);
    questRepository.save(quest);
    saveVector(memberId, dto);

    return CreateQuestResponse.fromEntity(quest);
  }

  private void saveVector(Long memberId, CreateQuestRequestDto dto) {
    CreateVectorDto vectorDto =
        CreateVectorDto.builder().memberId(memberId).quests(List.of(dto.title())).build();
    vectorService.save(vectorDto);
  }
}
