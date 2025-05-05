package com.groom.orbit.resume.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.goal.goal.repository.entity.MemberGoal;
import com.groom.orbit.goal.membergoal.application.MemberGoalService;
import com.groom.orbit.member.member.application.MemberQueryService;
import com.groom.orbit.member.member.repository.jpa.entity.Member;
import com.groom.orbit.resume.application.dto.ResumeRequestDto;
import com.groom.orbit.resume.application.dto.ResumeResponseDto;
import com.groom.orbit.resume.repository.ResumeRepository;
import com.groom.orbit.resume.repository.entity.Resume;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeCreateService {

  private final MemberQueryService memberQueryService;
  private final MemberGoalService memberGoalService;

  private final ResumeRepository resumeRepository;

  public CommonSuccessDto createResume(Long memberId, ResumeRequestDto request) {
    Member member = memberQueryService.findMember(memberId);

    resumeRepository.save(request.toResume(member));

    return CommonSuccessDto.fromEntity(true);
  }

  public ResumeResponseDto createResumeFromMemberGoal(
      Long memberId, Long memberGoalId, ResumeRequestDto requestDto) {
    MemberGoal memberGoal = memberGoalService.findByMemberIdAndId(memberId, memberGoalId);
    Member member = memberGoal.getMember();
    Resume resume = requestDto.toResume(member);

    resume.createFromMemberGoal(memberGoal);
    resumeRepository.save(resume);

    return ResumeResponseDto.fromResume(resume);
  }
}
