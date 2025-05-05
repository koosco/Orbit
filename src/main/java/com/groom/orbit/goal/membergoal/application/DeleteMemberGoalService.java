package com.groom.orbit.goal.membergoal.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.goal.goal.repository.MemberGoalRepository;
import com.groom.orbit.goal.goal.repository.entity.Goal;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteMemberGoalService {

  private final MemberGoalRepository memberGoalRepository;
  private final MemberGoalQueryService memberGoalQueryService;

  public CommonSuccessDto deleteMemberGoal(Long memberId, Long memberGoalId) {
    MemberGoal memberGoal = memberGoalQueryService.findMemberGoal(memberGoalId);

    memberGoal.validateMember(memberId);
    Goal goal = memberGoal.getGoal();
    goal.decreaseCount();
    memberGoalRepository.delete(memberGoal);

    return new CommonSuccessDto(true);
  }
}
