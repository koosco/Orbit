package com.groom.orbit.goal.quest.app;

import org.springframework.stereotype.Service;

import com.groom.orbit.goal.goal.app.MemberGoalService;
import com.groom.orbit.goal.goal.dao.entity.MemberGoal;
import com.groom.orbit.goal.quest.app.dto.response.RecommendQuestListResponseDto;
import com.groom.orbit.infra.ai.app.AiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestRecommendService {

  private final AiService aiService;
  private final MemberGoalService memberGoalService;

  public RecommendQuestListResponseDto recommendQuest(Long memberId, Long memberGoalId) {
    MemberGoal memberGoal = memberGoalService.findMemberGoal(memberGoalId);
    return aiService.recommendQuest(memberId, memberGoal);
  }
}
