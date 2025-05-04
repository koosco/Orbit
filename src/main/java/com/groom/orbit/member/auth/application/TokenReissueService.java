package com.groom.orbit.member.auth.application;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.member.auth.application.oauth.AuthToken;
import com.groom.orbit.member.auth.application.oauth.kakao.KakaoReissueParams;
import com.groom.orbit.member.auth.application.provider.JwtTokenProvider;
import com.groom.orbit.member.member.repository.jpa.MemberRepository;
import com.groom.orbit.member.member.repository.jpa.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenReissueService {

  private final MemberRepository memberRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisTemplate<String, String> redisTemplate;

  @Value("${jwt.refresh-token-validity}")
  private Long refreshTokenValidityMilliseconds;

  public AuthToken reissue(KakaoReissueParams params) {

    String refreshToken = params.getRefreshToken();

    Long memberId = jwtTokenProvider.parseRefreshToken(refreshToken);

    if (!refreshToken.equals(redisTemplate.opsForValue().get(memberId.toString()))) {
      throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);
    }

    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MEMBER));

    String newAccessToken = jwtTokenProvider.generateAccessToken(member.getId());

    String newRefreshToken = jwtTokenProvider.generateRefreshToken(member.getId());

    redisTemplate
        .opsForValue()
        .set(
            member.getId().toString(),
            newRefreshToken,
            refreshTokenValidityMilliseconds,
            TimeUnit.MILLISECONDS);

    return AuthToken.of(newAccessToken, newRefreshToken);
  }
}
