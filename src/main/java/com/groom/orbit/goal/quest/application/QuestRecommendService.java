package com.groom.orbit.goal.quest.application;

import org.springframework.stereotype.Service;

import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.goal.membergoal.application.MemberGoalQueryService;
import com.groom.orbit.goal.quest.application.dto.response.RecommendQuestListResponseDto;
import com.groom.orbit.infra.ai.application.AiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestRecommendService {

  private final AiService aiService;
  private final MemberGoalQueryService memberGoalQueryService;

  public RecommendQuestListResponseDto recommendQuest(Long memberId, Long memberGoalId) {
    MemberGoal memberGoal = memberGoalQueryService.findMemberGoalById(memberGoalId);
    return aiService.recommendQuest(memberId, memberGoal);
  }
}
