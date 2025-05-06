package com.groom.orbit.goal.quest.repository.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import com.groom.orbit.common.repository.entity.BaseTimeEntity;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.member.member.repository.jpa.entity.Member;

import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@DynamicUpdate
@Table(name = "quest")
@NoArgsConstructor
@AllArgsConstructor
public class Quest extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "quest_id")
  private Long questId;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(nullable = false)
  private Boolean isComplete = false;

  private LocalDate deadline;

  private Integer sequence;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_goal_id")
  private MemberGoal memberGoal;

  public static Quest create(
      String title, MemberGoal memberGoal, LocalDate deadline, int newSequence) {
    Quest quest =
        Quest.builder()
            .title(title)
            .memberGoal(memberGoal)
            .deadline(deadline)
            .sequence(newSequence)
            .build();
    memberGoal.getQuests().add(quest);

    return quest;
  }

  public static Quest copyQuest(String title, MemberGoal memberGoal) {
    Quest quest = Quest.builder().title(title).memberGoal(memberGoal).build();
    memberGoal.getQuests().add(quest);

    return quest;
  }

  public void validateMember(Long memberId) {
    Member member = this.memberGoal.getMember();
    member.validateId(memberId);
  }

  public void update(String title, Boolean isComplete, LocalDate deadline) {
    if (title != null && !title.equals(this.title)) {
      this.title = title;
    }
    if (isComplete != null && !isComplete.equals(this.isComplete)) {
      this.isComplete = isComplete;
    }
    if (deadline != null && !deadline.equals(this.deadline)) {
      this.deadline = deadline;
    }
  }
}
