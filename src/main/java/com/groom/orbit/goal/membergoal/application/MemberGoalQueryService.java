package com.groom.orbit.goal.membergoal.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.goal.goal.application.dto.response.GetMemberGoalResponseDto;
import com.groom.orbit.goal.goal.repository.MemberGoalRepository;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.goal.quest.application.dto.response.GetQuestResponseDto;
import com.groom.orbit.goal.quest.repository.entity.Quest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberGoalQueryService {

  private final MemberGoalRepository memberGoalRepository;

  public MemberGoal findMemberGoal(Long memberGoalId) {
    return memberGoalRepository
        .findById(memberGoalId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_GOAL));
  }

  public MemberGoal findMemberGoal(Long memberId, Long goalId) {
    return memberGoalRepository
        .findById(memberId, goalId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_GOAL));
  }

  public MemberGoal findByMemberIdAndId(Long memberId, Long memberGoalId) {
    return memberGoalRepository
        .findById(memberId, memberGoalId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MEMBER_GOAL));
  }

  public List<GetMemberGoalResponseDto> findGoals(Long memberId, Boolean isComplete) {
    if (isComplete == null) {
      isComplete = false;
    }

    List<MemberGoal> memberGoals =
        memberGoalRepository.findByMemberIdAndIsComplete(memberId, isComplete);

    return memberGoals.stream()
        .map(
            memberGoal ->
                new GetMemberGoalResponseDto(
                    memberGoal.getMemberGoalId(),
                    memberGoal.getTitle(),
                    memberGoal.getGoal().getCategory(),
                    memberGoal.getIsComplete(),
                    memberGoal.getSequence(),
                    memberGoal.getIsResume(),
                    memberGoal.getCreatedAt().toLocalDate(),
                    memberGoal.getCompletedDate().toLocalDate(),
                    getGetQuestResponseDtos(memberGoal)))
        .toList();
  }

  private static List<GetQuestResponseDto> getGetQuestResponseDtos(MemberGoal mg) {
    return mg.getQuests().stream()
        .map(
            q ->
                new GetQuestResponseDto(
                    q.getQuestId(), q.getTitle(), q.getDeadline(), q.getIsComplete()))
        .toList();
  }

  public GetMemberGoalResponseDto findGoal(Long memberGoalId) {
    MemberGoal memberGoal = findMemberGoal(memberGoalId);
    List<Quest> quests = memberGoal.getQuests();

    List<GetQuestResponseDto> questDtos =
        quests.stream()
            .map(
                quest ->
                    new GetQuestResponseDto(
                        quest.getQuestId(),
                        quest.getTitle(),
                        quest.getDeadline(),
                        quest.getIsComplete()))
            .toList();

    return new GetMemberGoalResponseDto(
        memberGoal.getMemberGoalId(),
        memberGoal.getTitle(),
        memberGoal.getGoal().getCategory(),
        memberGoal.getIsComplete(),
        memberGoal.getSequence(),
        memberGoal.getIsResume(),
        memberGoal.getCreatedAt().toLocalDate(),
        memberGoal.getCompletedDate().toLocalDate(),
        questDtos);
  }

  public List<MemberGoal> findMemberGoalsByGoalId(Long goalId) {
    return memberGoalRepository.findAllByGoalId(goalId);
  }
}
