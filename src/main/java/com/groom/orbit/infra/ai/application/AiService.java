package com.groom.orbit.infra.ai.application;

import com.groom.orbit.goal.goal.application.dto.response.RecommendGoalListResponseDto;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.goal.quest.application.dto.response.RecommendQuestListResponseDto;
import com.groom.orbit.member.member.application.dto.response.GetFeedbackResponseDto;
import com.groom.orbit.resume.application.dto.GetResumeResponseDto;

public interface AiService {

  GetFeedbackResponseDto getMemberFeedback(String interestJobs, GetResumeResponseDto dto);

  RecommendGoalListResponseDto recommendGoal(Long memberId);

  RecommendQuestListResponseDto recommendQuest(Long memberId, MemberGoal memberGoal);
}
