package com.groom.orbit.resume.application.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.groom.orbit.resume.repository.entity.Resume;
import com.groom.orbit.resume.repository.entity.ResumeCategory;

public record ResumeResponseDto(
    Long resumeId,
    ResumeCategory resumeCategory,
    String title,
    String content,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

  public static ResumeResponseDto fromResume(Resume resume) {
    return new ResumeResponseDto(
        resume.getResumeId(),
        resume.getResumeCategory(),
        resume.getTitle(),
        resume.getContent(),
        resume.getStartDate(),
        resume.getEndDate());
  }
}
