package com.groom.orbit.resume.application.dto;

import java.time.LocalDate;

import com.groom.orbit.member.member.repository.jpa.entity.Member;
import com.groom.orbit.resume.repository.entity.Resume;
import com.groom.orbit.resume.repository.entity.ResumeCategory;

public record ResumeRequestDto(
    ResumeCategory resumeCategory,
    String title,
    String content,
    LocalDate startDate,
    LocalDate endDate) {

  public Resume toResume(Member member) {
    return Resume.builder()
        .resumeCategory(this.resumeCategory)
        .title(this.title)
        .content(this.content)
        .startDate(this.startDate)
        .endDate(this.endDate)
        .member(member)
        .build();
  }
}
