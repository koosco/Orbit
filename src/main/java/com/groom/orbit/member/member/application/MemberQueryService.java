package com.groom.orbit.member.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.member.member.application.dto.response.GetMemberProfileResponseDto;
import com.groom.orbit.member.member.repository.jpa.MemberRepository;
import com.groom.orbit.member.member.repository.jpa.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

  private final MemberRepository memberRepository;

  public Member findMember(Long memberId) {
    return memberRepository
        .findById(memberId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MEMBER));
  }

  public GetMemberProfileResponseDto getMemberProfile(Long memberId) {
    Member member = findMember(memberId);

    return GetMemberProfileResponseDto.fromMember(member);
  }
}
