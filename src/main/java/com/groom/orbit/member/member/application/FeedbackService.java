package com.groom.orbit.member.member.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.infra.ai.application.AiService;
import com.groom.orbit.job.application.InterestJobService;
import com.groom.orbit.job.application.dto.JobDetailResponseDto;
import com.groom.orbit.member.member.application.dto.response.GetFeedbackResponseDto;
import com.groom.orbit.member.member.application.dto.response.GetMemberAiFeedbackResponseDto;
import com.groom.orbit.member.member.repository.jpa.MemberRepository;
import com.groom.orbit.member.member.repository.jpa.entity.Member;
import com.groom.orbit.resume.application.ResumeQueryService;
import com.groom.orbit.resume.application.dto.GetResumeResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

  private final AiService aiService;
  private final InterestJobService interestJobService;
  private final ResumeQueryService resumeQueryService;
  private final MemberQueryService memberQueryService;
  private final MemberRepository memberRepository;

  @Transactional
  public GetFeedbackResponseDto getFeedback(Long memberId) {
    String interestJobs = getInterestJobs(memberId);
    GetResumeResponseDto dto = resumeQueryService.getMyResume(memberId);

    GetFeedbackResponseDto aiResponse = aiService.getMemberFeedback(interestJobs, dto);
    Member member = memberQueryService.findMember(memberId);
    member.setAiFeedback(aiResponse.feedback());

    return aiResponse;
  }

  public GetMemberAiFeedbackResponseDto getMemberAiFeedback(Long memberId) {
    Member member = memberQueryService.findMember(memberId);

    return GetMemberAiFeedbackResponseDto.fromMember(member);
  }

  private String getInterestJobs(Long memberId) {
    List<String> jobs =
        interestJobService.findJobsByMemberId(memberId).stream()
            .map(JobDetailResponseDto::name)
            .toList();
    return String.join(",", jobs);
  }
}
