package com.groom.orbit.goal.membergoal.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.goal.goal.application.dto.response.GetMemberGoalResponseDto;
import com.groom.orbit.goal.goal.repository.MemberGoalRepository;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberGoalQueryService {

  private final MemberGoalRepository memberGoalRepository;

  public MemberGoal findMemberGoalById(Long memberGoalId) {
    return memberGoalRepository
        .findById(memberGoalId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_GOAL));
  }

  public List<MemberGoal> findAllMemberGoal(Long goalId) {
    return memberGoalRepository.findAllWithQuestsByGoalId(goalId);
  }

  public MemberGoal findMemberGoalByMemberIdAndGoalId(Long memberId, Long goalId) {
    return memberGoalRepository
        .findById(memberId, goalId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_GOAL));
  }

  public MemberGoal findByMemberGoalByMemberIdAndMemberGoalId(Long memberId, Long memberGoalId) {
    return memberGoalRepository
        .findById(memberId, memberGoalId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MEMBER_GOAL));
  }

  public List<GetMemberGoalResponseDto> findMemberGoals(Long memberId, Boolean isComplete) {
    if (isComplete == null) {
      isComplete = false;
    }

    List<MemberGoal> memberGoals =
        memberGoalRepository.findByMemberIdAndIsComplete(memberId, isComplete);

    return memberGoals.stream().map(GetMemberGoalResponseDto::from).toList();
  }

  public GetMemberGoalResponseDto findGoal(Long memberGoalId) {
    MemberGoal memberGoal = findMemberGoalById(memberGoalId);
    return GetMemberGoalResponseDto.from(memberGoal);
  }

  public List<MemberGoal> findMemberGoalsByGoalId(Long goalId) {
    return memberGoalRepository.findAllByGoalId(goalId);
  }
}
