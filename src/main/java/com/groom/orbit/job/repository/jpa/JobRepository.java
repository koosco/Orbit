package com.groom.orbit.job.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.job.repository.jpa.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {}
