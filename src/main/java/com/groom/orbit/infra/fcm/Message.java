package com.groom.orbit.infra.fcm;

public record Message(Notification notification, String token) {

  public static Message of(Notification notification, String token) {
    return new Message(notification, token);
  }
}
