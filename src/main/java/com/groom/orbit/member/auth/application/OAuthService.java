package com.groom.orbit.member.auth.application;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.infra.ai.application.VectorService;
import com.groom.orbit.infra.ai.application.dto.CreateVectorDto;
import com.groom.orbit.member.auth.application.dto.LoginResponseDto;
import com.groom.orbit.member.auth.application.oauth.AuthTokenGenerator;
import com.groom.orbit.member.auth.application.oauth.OAuthInfoResponse;
import com.groom.orbit.member.auth.application.oauth.OAuthLoginParams;
import com.groom.orbit.member.auth.application.oauth.RequestOAuthInfoService;
import com.groom.orbit.member.member.repository.jpa.MemberRepository;
import com.groom.orbit.member.member.repository.jpa.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuthService {

  private final MemberRepository memberRepository;
  private final VectorService vectorService;
  private final AuthTokenGenerator authTokensGenerator;
  private final RequestOAuthInfoService requestOAuthInfoService;

  public LoginResponseDto login(OAuthLoginParams params) {
    OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
    Member member = findOrCreateMember(oAuthInfoResponse);
    member.setLastLogin(LocalDate.now());
    return LoginResponseDto.fromLogin(
        authTokensGenerator.generate(member.getId()), member.getKakaoNickname());
  }

  public Member findOrCreateMember(OAuthInfoResponse response) {
    return memberRepository
        .findByKakaoNickname(response.getKakaoNickname())
        .orElseGet(() -> registerNewMember(response));
  }

  private Member registerNewMember(OAuthInfoResponse response) {
    Member member =
        Member.builder()
            .imageUrl(response.getKakaoImage())
            .kakaoNickname(response.getKakaoNickname())
            .nickname(response.getKakaoNickname())
            .build();

    Member savedMember = memberRepository.save(member);
    createMemberVector(savedMember);
    return savedMember;
  }

  private void createMemberVector(Member member) {
    CreateVectorDto vectorDto = CreateVectorDto.builder().memberId(member.getId()).build();
    vectorService.save(vectorDto);
  }
}
