package com.groom.orbit.goal.quest.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.goal.membergoal.application.MemberGoalQueryService;
import com.groom.orbit.goal.quest.application.dto.response.GetQuestResponseDto;
import com.groom.orbit.goal.quest.repository.QuestRepository;
import com.groom.orbit.goal.quest.repository.entity.Quest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestQueryService {

  private final MemberGoalQueryService memberGoalQueryService;

  private final QuestRepository questRepository;

  public Quest findQuest(Long questId) {
    return questRepository
        .findById(questId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_QUEST));
  }

  public List<Quest> findQuestsByMemberAndGoal(Long memberGoalId) {
    return questRepository.findByMemberGoalId(memberGoalId);
  }

  public List<GetQuestResponseDto> findQuestsByGoalId(Long memberGoalId) {
    List<Quest> quests = findQuestsByMemberAndGoal(memberGoalId);

    return quests.stream()
        .map(
            quest ->
                new GetQuestResponseDto(
                    quest.getQuestId(),
                    quest.getTitle(),
                    quest.getDeadline(),
                    quest.getIsComplete()))
        .toList();
  }

  public int getQuestCountsByGoalId(Long goalId) {
    return questRepository.getCountByGoalId(goalId);
  }

  public List<String> getRecommendedQuests(Long memberGoalId) {
    MemberGoal memberGoal = memberGoalQueryService.findMemberGoal(memberGoalId);
    Set<Quest> myQuests = new HashSet<>(memberGoal.getQuests());

    List<MemberGoal> memberGoals =
        memberGoalQueryService.findMemberGoalsByGoalId(
            memberGoal.getGoal().getGoalId()); // goals 조회
    Set<Quest> quests =
        memberGoals.stream()
            .map(MemberGoal::getQuests)
            .flatMap(List::stream)
            .collect(Collectors.toSet());

    return quests.stream().filter(quest -> !myQuests.contains(quest)).map(Quest::getTitle).toList();
  }
}
