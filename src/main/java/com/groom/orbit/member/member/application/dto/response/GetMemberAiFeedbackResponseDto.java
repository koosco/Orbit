package com.groom.orbit.member.member.application.dto.response;

import com.groom.orbit.member.member.repository.jpa.entity.Member;

public record GetMemberAiFeedbackResponseDto(String aiFeedback) {

  public static GetMemberAiFeedbackResponseDto fromMember(Member member) {
    return new GetMemberAiFeedbackResponseDto(member.getAiFeedback());
  }
}
