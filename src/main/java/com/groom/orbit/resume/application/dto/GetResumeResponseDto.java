package com.groom.orbit.resume.application.dto;

import java.util.List;

public record GetResumeResponseDto(
    List<ResumeResponseDto> academyList,
    List<ResumeResponseDto> careerList,
    List<ResumeResponseDto> qualificationList,
    List<ResumeResponseDto> experienceList,
    List<ResumeResponseDto> etcList) {}
