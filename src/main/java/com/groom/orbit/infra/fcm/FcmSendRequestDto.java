package com.groom.orbit.infra.fcm;

public record FcmSendRequestDto(String fcmToken, String title, String body) {

  public static FcmSendRequestDto of(String fcmToken, String title, String body) {
    return new FcmSendRequestDto(fcmToken, title, body);
  }
}
