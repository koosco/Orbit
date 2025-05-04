package com.groom.orbit.member.auth.app.oauth;

import com.groom.orbit.member.auth.app.kakao.KakaoReissueParams;

public interface OAuthApiClient {

  String requestAccessToken(OAuthLoginParams params);

  OAuthInfoResponse requestOauthInfo(String accessToken);

  String reissueAccessToken(KakaoReissueParams params);
}
