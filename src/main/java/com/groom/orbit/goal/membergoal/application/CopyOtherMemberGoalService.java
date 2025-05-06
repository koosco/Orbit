package com.groom.orbit.goal.membergoal.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.goal.goal.application.GoalQueryService;
import com.groom.orbit.goal.goal.application.dto.response.GetMemberGoalResponseDto;
import com.groom.orbit.goal.goal.repository.MemberGoalRepository;
import com.groom.orbit.goal.goal.repository.entity.Goal;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.member.member.application.MemberQueryService;
import com.groom.orbit.member.member.repository.jpa.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CopyOtherMemberGoalService {

  private final MemberQueryService memberQueryService;
  private final GoalQueryService goalQueryService;
  private final MemberGoalQueryService memberGoalQueryService;

  private final MemberGoalRepository memberGoalRepository;

  public GetMemberGoalResponseDto createOtherGoal(Long memberId, Long memberGoalId) {

    MemberGoal originalMemberGoal = memberGoalQueryService.findMemberGoalById(memberGoalId);
    Goal goal = goalQueryService.findGoal(originalMemberGoal.getMemberGoalId());
    Member member = memberQueryService.findMember(memberId);
    int memberGoalSize = memberGoalRepository.findAllByMemberIdAndIsCompleteFalse(memberId).size();

    MemberGoal copyMemberGoal =
        MemberGoal.copyMemberGoal(originalMemberGoal, member, goal, memberGoalSize);
    memberGoalRepository.save(copyMemberGoal);

    return GetMemberGoalResponseDto.from(copyMemberGoal);
  }
}
