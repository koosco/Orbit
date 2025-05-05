package com.groom.orbit.goal.membergoal.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.goal.goal.repository.MemberGoalRepository;
import com.groom.orbit.goal.goal.repository.entity.GoalCategory;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberGoalService {

  private final MemberGoalRepository memberGoalRepository;

  public MemberGoal findMemberGoal(Long memberGoalId) {
    return memberGoalRepository
        .findById(memberGoalId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_GOAL));
  }

  public List<MemberGoal> findAllMemberGoal(Long goalId) {
    return memberGoalRepository.findAllWithQuestsByGoalId(goalId);
  }

  /** 처음부터 jobIds를 선택하지 않은 경우 */
  public Page<MemberGoal> findMemberGoal(String category, Pageable pageable) {
    String order =
        pageable.getSort().stream().findFirst().map(Order::getProperty).orElseGet(() -> "latest");
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
        pageable.getSort().stream().findFirst().map(Order::getProperty).orElseGet(() -> "latest");

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
