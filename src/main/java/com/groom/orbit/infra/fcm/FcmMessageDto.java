package com.groom.orbit.infra.fcm;

public record FcmMessageDto(Boolean validateOnly, Message message) {

  public static FcmMessageDto of(Boolean validateOnly, Message message) {
    return new FcmMessageDto(validateOnly, message);
  }
}
