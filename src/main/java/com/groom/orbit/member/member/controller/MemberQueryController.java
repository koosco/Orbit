package com.groom.orbit.member.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.member.member.application.MemberQueryService;
import com.groom.orbit.member.member.application.dto.response.GetMemberProfileResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberQueryController {

  private final MemberQueryService memberQueryService;

  @GetMapping
  public ResponseDto<GetMemberProfileResponseDto> getMemberProfile(@AuthMember Long memberId) {
    return ResponseDto.ok(memberQueryService.getMemberProfile(memberId));
  }
}
