package com.groom.orbit.infra.ai.application;

import java.util.List;

import com.groom.orbit.infra.ai.application.dto.CreateVectorDto;
import com.groom.orbit.infra.ai.application.dto.UpdateVectorGoalDto;
import com.groom.orbit.infra.ai.application.dto.UpdateVectorQuestDto;
import com.groom.orbit.infra.ai.repository.vector.Vector;

public interface VectorService {

  List<Vector> findSimilarVector(Long memberId);

  void save(CreateVectorDto dto);

  void updateGoal(UpdateVectorGoalDto dto);

  void updateQuest(UpdateVectorQuestDto updateDto);
}
