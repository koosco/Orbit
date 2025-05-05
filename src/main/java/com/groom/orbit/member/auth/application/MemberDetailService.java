package com.groom.orbit.member.auth.application;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.member.auth.application.domain.MemberDetails;
import com.groom.orbit.member.member.application.MemberQueryService;
import com.groom.orbit.member.member.repository.jpa.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

  private final MemberQueryService memberQueryService;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    Member member = memberQueryService.findMember(Long.parseLong(userId));

    return new MemberDetails(member);
  }
}
