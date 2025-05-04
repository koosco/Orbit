package com.groom.orbit.job.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.infra.ai.application.VectorService;
import com.groom.orbit.infra.ai.application.dto.CreateVectorDto;
import com.groom.orbit.job.application.dto.InterestJobRequestDto;
import com.groom.orbit.job.repository.jpa.InterestJobRepository;
import com.groom.orbit.job.repository.jpa.entity.InterestJob;
import com.groom.orbit.job.repository.jpa.entity.Job;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobCommandService {

  private final JobQueryService jobQueryService;
  private final InterestJobService interestJobService;
  private final InterestJobRepository interestJobRepository;
  private final VectorService vectorService;

  public CommonSuccessDto saveInterestJob(Long memberId, InterestJobRequestDto dto) {

    List<InterestJob> interestJobList = interestJobRepository.findAllByMemberId(memberId);
    interestJobRepository.deleteAll(interestJobList);
    List<Job> jobs = jobQueryService.findJobsByIds(dto.ids());
    interestJobService.saveInterestJob(memberId, jobs);

    saveVector(memberId, jobs);

    return CommonSuccessDto.fromEntity(true);
  }

  private void saveVector(Long memberId, List<Job> interestJobList) {
    CreateVectorDto vectorDto =
        CreateVectorDto.builder()
            .memberId(memberId)
            .interestJobs(interestJobList.stream().map(Job::getName).toList())
            .build();
    vectorService.save(vectorDto);
  }
}
