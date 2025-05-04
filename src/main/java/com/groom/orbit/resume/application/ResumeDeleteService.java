package com.groom.orbit.resume.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.resume.repository.ResumeRepository;
import com.groom.orbit.resume.repository.entity.Resume;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeDeleteService {

  private final ResumeQueryService resumeQueryService;
  private final ResumeRepository resumeRepository;

  public CommonSuccessDto deleteResume(Long memberId, Long resumeId) {
    Resume resume = resumeQueryService.findResume(resumeId);
    resume.validate(memberId);

    resume.delete();
    resumeRepository.deleteById(resumeId);

    return CommonSuccessDto.fromEntity(true);
  }
}
