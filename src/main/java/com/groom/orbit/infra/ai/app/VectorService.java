package com.groom.orbit.infra.ai.app;

import java.util.List;

import com.groom.orbit.infra.ai.app.dto.CreateVectorDto;
import com.groom.orbit.infra.ai.app.dto.UpdateVectorGoalDto;
import com.groom.orbit.infra.ai.app.dto.UpdateVectorQuestDto;
import com.groom.orbit.infra.ai.dao.vector.Vector;

public interface VectorService {

  List<Vector> findSimilarVector(Long memberId);

  void save(CreateVectorDto dto);

  void updateGoal(UpdateVectorGoalDto dto);

  void updateQuest(UpdateVectorQuestDto updateDto);
}
