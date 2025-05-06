package com.groom.orbit.goal.membergoal.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.goal.goal.application.GoalResolver;
import com.groom.orbit.goal.goal.application.dto.request.MemberGoalRequestDto;
import com.groom.orbit.goal.goal.repository.MemberGoalRepository;
import com.groom.orbit.goal.goal.repository.entity.Goal;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.infra.ai.application.VectorService;
import com.groom.orbit.infra.ai.application.dto.UpdateVectorGoalDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateMemberGoalService {

  private final MemberGoalQueryService memberGoalQueryService;
  private final VectorService vectorService;
  private final GoalResolver goalResolver;

  private final MemberGoalRepository memberGoalRepository;

  public CommonSuccessDto updateMemberGoal(
      Long memberId, Long memberGoalId, MemberGoalRequestDto dto) {
    MemberGoal memberGoal = memberGoalQueryService.findMemberGoalById(memberGoalId);
    Goal goal = goalResolver.findOrCreateGoal(dto.title(), dto.category());
    memberGoal.validateMember(memberId);

    updateVector(memberId, dto, memberGoal);
    memberGoal.update(goal);

    return new CommonSuccessDto(true);
  }

  public CommonSuccessDto updateMemberGoalIsComplete(Long memberId, Long memberGoalId) {

    MemberGoal memberGoal = memberGoalQueryService.findMemberGoalById(memberGoalId);

    memberGoal.setIsComplete(true);
    memberGoal.setCompletedDate(LocalDateTime.now());

    List<MemberGoal> memberGoalList =
        memberGoalRepository.findAllByMemberIdAndIsCompleteFalseAndSequenceGreaterThan(
            memberId, memberGoal.getSequence().longValue());

    memberGoalList.forEach(goal -> goal.setSequence(goal.getSequence() - 1));

    memberGoalRepository.save(memberGoal);
    memberGoalRepository.saveAll(memberGoalList);

    return CommonSuccessDto.fromEntity(true);
  }

  private void updateVector(Long memberId, MemberGoalRequestDto dto, MemberGoal memberGoal) {
    if (dto.title() == null) {
      return;
    }

    UpdateVectorGoalDto updateDto =
        UpdateVectorGoalDto.builder()
            .memberId(memberId)
            .goal(memberGoal.getTitle())
            .newGoal(dto.title())
            .build();
    vectorService.updateGoal(updateDto);
  }
}
