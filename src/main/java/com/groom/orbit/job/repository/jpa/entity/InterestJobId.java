package com.groom.orbit.job.repository.jpa.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class InterestJobId implements Serializable {

  private Long job;
  private Long member;
}
