package com.groom.orbit.resume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.resume.repository.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

  List<Resume> findAllByMemberId(Long memberId);
}
