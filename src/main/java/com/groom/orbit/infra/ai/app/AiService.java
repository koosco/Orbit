package com.groom.orbit.infra.ai.app;

import com.groom.orbit.goal.goal.app.dto.response.RecommendGoalListResponseDto;
import com.groom.orbit.goal.goal.dao.entity.MemberGoal;
import com.groom.orbit.goal.quest.app.dto.response.RecommendQuestListResponseDto;
import com.groom.orbit.member.member.app.dto.response.GetFeedbackResponseDto;
import com.groom.orbit.resume.application.dto.GetResumeResponseDto;

public interface AiService {

  GetFeedbackResponseDto getMemberFeedback(String interestJobs, GetResumeResponseDto dto);

  RecommendGoalListResponseDto recommendGoal(Long memberId);

  RecommendQuestListResponseDto recommendQuest(Long memberId, MemberGoal memberGoal);
}
