package com.james.LMS.service;

import com.james.LMS.entity.TrainingSession;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface TrainingSessionService {
  CompletableFuture<Integer> countInMonthBy(Month month, Long groupId);

  Optional<TrainingSession> findWithCurriculumTrainingSetsById(Long id);

  CompletableFuture<List<TrainingSession>> findByGroupIdAndStartedAtBetween(
      Long groupId, Month month);

  CompletableFuture<Map<String, Integer>> findTotalCurriculumsInTrainingSetsMapByMonthAndGroupId(
      Month month, Long groupId);

  void save(TrainingSession trainingSession);
}
