package com.groom.orbit.goal.membergoal.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.goal.goal.application.dto.response.GetMemberGoalResponseDto;
import com.groom.orbit.goal.goal.application.query.GoalQueryService;
import com.groom.orbit.goal.goal.repository.MemberGoalRepository;
import com.groom.orbit.goal.goal.repository.entity.Goal;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.goal.quest.application.dto.response.GetQuestResponseDto;
import com.groom.orbit.goal.quest.repository.QuestRepository;
import com.groom.orbit.goal.quest.repository.entity.Quest;
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

  private final QuestRepository questRepository;
  private final MemberGoalRepository memberGoalRepository;

  public GetMemberGoalResponseDto createOtherGoal(Long memberId, Long memberGoalId) {

    MemberGoal memberGoal = memberGoalQueryService.findMemberGoal(memberGoalId);
    Goal goal = goalQueryService.findGoal(memberGoal.getMemberGoalId());
    Member member = memberQueryService.findMember(memberId);
    int memberGoalLen = memberGoalRepository.findAllByMemberIdAndIsCompleteFalse(memberId).size();

    MemberGoal copyMemberGoal = new MemberGoal().copyMemberGoal(member, goal, memberGoalLen);

    memberGoalRepository.save(copyMemberGoal);

    List<Quest> copQuestList =
        memberGoal.getQuests().stream()
            .map(originQuest -> Quest.copyQuest(originQuest.getTitle(), copyMemberGoal))
            .toList();

    questRepository.saveAll(copQuestList);

    List<GetQuestResponseDto> questDtos =
        copQuestList.stream()
            .map(
                quest ->
                    new GetQuestResponseDto(
                        quest.getQuestId(),
                        quest.getTitle(),
                        quest.getDeadline(),
                        quest.getIsComplete()))
            .toList();

    return new GetMemberGoalResponseDto(
        copyMemberGoal.getMemberGoalId(),
        copyMemberGoal.getTitle(),
        copyMemberGoal.getGoal().getCategory(),
        copyMemberGoal.getIsComplete(),
        copyMemberGoal.getSequence(),
        copyMemberGoal.getIsResume(),
        copyMemberGoal.getCreatedAt().toLocalDate(),
        copyMemberGoal.getCompletedDate().toLocalDate(),
        questDtos);
  }
}
