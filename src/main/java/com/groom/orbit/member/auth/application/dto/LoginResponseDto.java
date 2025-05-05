package com.groom.orbit.member.auth.application.dto;

import com.groom.orbit.member.auth.application.oauth.AuthToken;

public record LoginResponseDto(AuthToken authToken, String nickname) {

  public static LoginResponseDto fromLogin(AuthToken authToken, String nickname) {
    return new LoginResponseDto(authToken, nickname);
  }
}
