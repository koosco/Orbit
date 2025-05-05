package com.groom.orbit.member.member.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.member.member.repository.jpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByKakaoNickname(String kakaoNickname);
}
