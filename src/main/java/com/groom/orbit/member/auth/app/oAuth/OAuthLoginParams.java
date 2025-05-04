package com.groom.orbit.member.auth.app.oAuth;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
  MultiValueMap<String, String> makeBody();
}
