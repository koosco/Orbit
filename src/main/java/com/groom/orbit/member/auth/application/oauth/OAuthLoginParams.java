package com.groom.orbit.member.auth.application.oauth;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
  MultiValueMap<String, String> makeBody();
}
