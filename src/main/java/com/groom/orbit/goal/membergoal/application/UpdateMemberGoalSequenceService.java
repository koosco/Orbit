package com.groom.orbit.goal.membergoal.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.goal.goal.application.dto.request.UpdateMemberGoalSequenceRequestDto;
import com.groom.orbit.goal.goal.repository.MemberGoalRepository;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateMemberGoalSequenceService {

  private final MemberGoalRepository memberGoalRepository;

  public CommonSuccessDto updateMemberGoalSequence(
      Long memberId, List<UpdateMemberGoalSequenceRequestDto> requestDtoList) {

    List<MemberGoal> memberGoalList =
        memberGoalRepository.findAllByMemberIdAndIsCompleteFalse(memberId);

    for (UpdateMemberGoalSequenceRequestDto dto : requestDtoList) {
      memberGoalList.stream()
          .filter(memberGoal -> memberGoal.getMemberGoalId().equals(dto.memberGoalId()))
          .findFirst()
          .ifPresent(memberGoal -> memberGoal.setSequence(dto.sequence()));
    }

    memberGoalRepository.saveAll(memberGoalList);

    return CommonSuccessDto.fromEntity(true);
  }
}
