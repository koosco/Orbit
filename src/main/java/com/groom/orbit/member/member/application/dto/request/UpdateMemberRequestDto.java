package com.groom.orbit.member.member.application.dto.request;

public record UpdateMemberRequestDto(
    String nickname,
    String knownPrompt,
    String helpPrompt,
    Boolean isNotification,
    Boolean isProfile) {}
