package com.groom.orbit.goal.membergoal.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.goal.goal.application.command.GoalCommandService;
import com.groom.orbit.goal.goal.application.dto.request.MemberGoalRequestDto;
import com.groom.orbit.goal.goal.application.dto.response.GetMemberGoalResponseDto;
import com.groom.orbit.goal.goal.application.query.GoalQueryService;
import com.groom.orbit.goal.goal.repository.MemberGoalRepository;
import com.groom.orbit.goal.goal.repository.entity.Goal;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.goal.quest.application.dto.response.GetQuestResponseDto;
import com.groom.orbit.goal.quest.repository.entity.Quest;
import com.groom.orbit.infra.ai.application.VectorService;
import com.groom.orbit.infra.ai.application.dto.CreateVectorDto;
import com.groom.orbit.member.member.application.MemberQueryService;
import com.groom.orbit.member.member.repository.jpa.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateMemberGoalService {

  private final MemberQueryService memberQueryService;
  private final GoalQueryService goalQueryService;
  private final GoalCommandService goalCommandService;
  private final VectorService vectorService;

  private final MemberGoalRepository memberGoalRepository;

  public GetMemberGoalResponseDto createGoal(Long memberId, MemberGoalRequestDto dto) {
    Member member = memberQueryService.findMember(memberId);
    Goal goal = findGoal(dto.title(), dto.category());
    int memberGoalSize = memberGoalRepository.findAllByMemberIdAndIsCompleteFalse(memberId).size();

    MemberGoal memberGoal = MemberGoal.create(member, goal, memberGoalSize);
    List<String> goalTitles = List.of(memberGoal.getTitle());
    List<String> questTitles =
        dto.quests().stream()
            .map(quest -> Quest.copyQuest(quest.title(), memberGoal))
            .map(Quest::getTitle)
            .toList();

    MemberGoal savedMemberGoal = memberGoalRepository.save(memberGoal);
    saveVector(memberId, goalTitles, questTitles);

    List<GetQuestResponseDto> questDtos =
        savedMemberGoal.getQuests().stream()
            .map(
                quest ->
                    new GetQuestResponseDto(
                        quest.getQuestId(),
                        quest.getTitle(),
                        quest.getDeadline(),
                        quest.getIsComplete()))
            .toList();

    return new GetMemberGoalResponseDto(
        savedMemberGoal.getMemberGoalId(),
        savedMemberGoal.getTitle(),
        savedMemberGoal.getGoal().getCategory(),
        savedMemberGoal.getIsComplete(),
        savedMemberGoal.getSequence(),
        savedMemberGoal.getIsResume(),
        savedMemberGoal.getCreatedAt().toLocalDate(),
        savedMemberGoal.getCompletedDate().toLocalDate(),
        questDtos);
  }

  private void saveVector(Long memberId, List<String> goalTitles, List<String> questTitles) {
    CreateVectorDto vectorDto =
        CreateVectorDto.builder().memberId(memberId).goals(goalTitles).quests(questTitles).build();
    vectorService.save(vectorDto);
  }

  private Goal findGoal(String title, String category) {
    Optional<Goal> findGoal = goalQueryService.findGoalByTitleAndCategory(title, category);

    return findGoal.orElseGet(() -> goalCommandService.createGoal(title, category));
  }
}
