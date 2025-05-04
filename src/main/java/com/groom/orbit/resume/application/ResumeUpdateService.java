package com.groom.orbit.resume.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.resume.application.dto.ResumeRequestDto;
import com.groom.orbit.resume.repository.entity.Resume;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeUpdateService {

  private final ResumeQueryService resumeQueryService;

  public CommonSuccessDto updateResume(Long memberId, Long resumeId, ResumeRequestDto requestDto) {
    Resume resume = resumeQueryService.findResume(resumeId);
    resume.validate(memberId);

    resume.update(requestDto);

    return CommonSuccessDto.fromEntity(true);
  }
}
