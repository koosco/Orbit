package com.groom.orbit.goal.goal.repository.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.groom.orbit.common.repository.entity.BaseTimeEntity;
import com.groom.orbit.goal.quest.repository.entity.Quest;
import com.groom.orbit.member.member.repository.jpa.entity.Member;

import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "member_goal")
public class MemberGoal extends BaseTimeEntity {

  private static final LocalDateTime DEFAULT_COMPLETED_TIME = LocalDateTime.of(2000, 12, 31, 0, 0);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_goal_id")
  private Long memberGoalId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "goal_id")
  private Goal goal;

  @ColumnDefault("false")
  @Setter
  @Column(nullable = false)
  private Boolean isComplete = false;

  @Setter
  @Column(name = "sequence")
  private Integer sequence;

  @Setter
  @Column(name = "completed_date")
  private LocalDateTime completedDate = DEFAULT_COMPLETED_TIME;

  @ColumnDefault("false")
  @Column(name = "is_resume")
  private Boolean isResume = false;

  @OneToMany(mappedBy = "memberGoal", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Quest> quests = new ArrayList<>();

  public static MemberGoal create(Member member, Goal goal, int memberGoalSize) {
    goal.increaseCount();
    return MemberGoal.builder().member(member).goal(goal).sequence(memberGoalSize + 1).build();
  }

  public static MemberGoal copyMemberGoal(
      MemberGoal originalMemberGoal, Member member, Goal goal, Integer memberGoalSize) {
    MemberGoal memberGoal =
        MemberGoal.builder()
            .member(member)
            .goal(goal)
            .isComplete(false)
            .sequence(memberGoalSize + 1)
            .isResume(false)
            .completedDate(DEFAULT_COMPLETED_TIME)
            .build();

    originalMemberGoal
        .getQuests()
        .forEach(
            originalQuest -> {
              Quest copiedQuest = Quest.copyQuest(originalQuest.getTitle(), memberGoal);
              memberGoal.getQuests().add(copiedQuest);
              copiedQuest.setMemberGoal(originalMemberGoal);
            });

    return memberGoal;
  }

  public String getTitle() {
    return this.goal.getTitle();
  }

  public void update(Goal goal) {
    this.goal.decreaseCount();
    goal.increaseCount();
    this.goal = goal;
  }

  public void validateMember(Long memberId) {
    this.member.validateId(memberId);
  }

  public void deleteResume() {
    this.isResume = false;
  }

  public void createResume() {
    this.isResume = true;
  }
}
