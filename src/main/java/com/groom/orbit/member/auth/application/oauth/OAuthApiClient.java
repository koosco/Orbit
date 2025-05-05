package com.groom.orbit.member.auth.application.oauth;

import com.groom.orbit.member.auth.application.oauth.kakao.KakaoReissueParams;

public interface OAuthApiClient {

  String requestAccessToken(OAuthLoginParams params);

  OAuthInfoResponse requestOauthInfo(String accessToken);

  String reissueAccessToken(KakaoReissueParams params);
}
