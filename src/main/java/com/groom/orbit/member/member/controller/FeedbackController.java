package com.groom.orbit.member.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.member.member.application.FeedbackService;
import com.groom.orbit.member.member.application.dto.response.GetFeedbackResponseDto;
import com.groom.orbit.member.member.application.dto.response.GetMemberAiFeedbackResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/feedback")
public class FeedbackController {

  private final FeedbackService feedbackService;

  @GetMapping
  public ResponseDto<GetMemberAiFeedbackResponseDto> getMemberFeedback(@AuthMember Long memberId) {
    return ResponseDto.ok(feedbackService.getMemberFeedback(memberId));
  }

  @PostMapping
  public ResponseDto<GetFeedbackResponseDto> generateMemberFeedback(@AuthMember Long memberId) {
    return ResponseDto.ok(feedbackService.generateFeedback(memberId));
  }
}
