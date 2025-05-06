package com.groom.orbit.goal.goal.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.goal.goal.application.dto.response.GoalSearchDetailResponseDto;
import com.groom.orbit.goal.goal.application.dto.response.GoalSearchResponseDto;
import com.groom.orbit.goal.goal.repository.MemberGoalRepository;
import com.groom.orbit.goal.goal.repository.entity.Goal;
import com.groom.orbit.goal.goal.repository.entity.GoalCategory;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.goal.membergoal.application.MemberGoalQueryService;
import com.groom.orbit.goal.quest.repository.entity.Quest;
import com.groom.orbit.job.application.InterestJobService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoalSearchService {

  private final MemberGoalQueryService memberGoalQueryService;
  private final InterestJobService interestJobService;

  private final MemberGoalRepository memberGoalRepository;

  public GoalSearchDetailResponseDto searchGoalByGoalId(Long goalId) {
    List<MemberGoal> memberGoals = memberGoalQueryService.findAllMemberGoal(goalId);
    Goal goal = searchGoalByGoalId(memberGoals);
    List<String> questTitles =
        memberGoals.stream()
            .map(MemberGoal::getQuests)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet())
            .stream()
            .sorted(Comparator.comparing(Quest::getCreatedAt).reversed())
            .map(Quest::getTitle)
            .toList();

    return new GoalSearchDetailResponseDto(goal.getCategory().name(), goal.getTitle(), questTitles);
  }

  private static Goal searchGoalByGoalId(List<MemberGoal> memberGoals) {
    if (memberGoals.isEmpty()) {
      throw new CommonException(ErrorCode.NOT_FOUND_MEMBER_GOAL);
    }
    return memberGoals.getFirst().getGoal();
  }

  public Page<GoalSearchResponseDto> searchGoals(
      Long memberId, String category, List<Long> jobIds, Pageable pageable) {
    Page<MemberGoal> memberGoals = findMemberGoal(memberId, category, jobIds, pageable);

    return memberGoals.map(GoalSearchResponseDto::from);
  }

  private Page<MemberGoal> findMemberGoal(
      Long memberId, String category, List<Long> jobIds, Pageable pageable) {
    if (jobIds == null) {
      return findMemberGoal(category, pageable);
    }
    List<Long> memberIds =
        new ArrayList<>(
            interestJobService.findMemberInInterestJob(jobIds).stream().distinct().toList());
    memberIds.remove(memberId);

    return findMemberGoalInMemberId(memberIds, category, pageable);
  }

  /** 처음부터 jobIds를 선택하지 않은 경우 */
  public Page<MemberGoal> findMemberGoal(String category, Pageable pageable) {
    String order =
        pageable.getSort().stream()
            .findFirst()
            .map(Sort.Order::getProperty)
            .orElseGet(() -> "latest");
    Pageable customPageable =
        Pageable.ofSize(pageable.getPageSize()).withPage(pageable.getPageNumber());
    GoalCategory goalCategory = GoalCategory.from(category);

    if (order.equals("latest")) {
      return memberGoalRepository.findByCategoryCreatedAtDesc(goalCategory, customPageable);
    }
    return memberGoalRepository.findByCategoryCountAtDesc(goalCategory, customPageable);
  }

  /** TODO 동적 쿼리 처리 -> querydsl */
  public Page<MemberGoal> findMemberGoalInMemberId(
      List<Long> memberIds, String category, Pageable pageable) {

    if (memberIds.isEmpty()) {
      return Page.empty(pageable);
    }

    String order =
        pageable.getSort().stream()
            .findFirst()
            .map(Sort.Order::getProperty)
            .orElseGet(() -> "latest");

    Pageable customPageable =
        Pageable.ofSize(pageable.getPageSize()).withPage(pageable.getPageNumber());
    GoalCategory goalCategory = GoalCategory.from(category);

    if (order.equals("latest")) {
      return memberGoalRepository.findByMemberIdsAndCategoryCreatedAtDesc(
          memberIds, goalCategory, customPageable);
    }
    return memberGoalRepository.findByMemberIdsAndCategoryCountDesc(
        memberIds, goalCategory, customPageable);
  }
}
