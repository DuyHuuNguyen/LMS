package com.james.LMS.service.impl;

import com.james.LMS.entity.TrainingSession;
import com.james.LMS.repository.TrainingSessionRepository;
import com.james.LMS.service.TrainingSessionService;
import com.james.LMS.util.DurationConverterUtil;
import com.james.LMS.util.SecurityUserDetailsUtil;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingSessionServiceImpl implements TrainingSessionService {
  private final TrainingSessionRepository trainingSessionRepository;
  private final ExecutorService newVirtualThreadPerTaskExecutor;

  @Override
  public CompletableFuture<Integer> countInMonthBy(Month month, Long groupId) {
    return CompletableFuture.supplyAsync(
        () ->
            this.trainingSessionRepository.countTrainingSessionByMonthAndId(
                month.getValue(), groupId),
        this.newVirtualThreadPerTaskExecutor);
  }

  @Override
  public CompletableFuture<Map<String, Integer>>
      findTotalCurriculumsInTrainingSetsMapByMonthAndGroupId(Month month, Long groupId) {
    return null;
  }

  @Override
  public void save(TrainingSession trainingSession) {
    this.trainingSessionRepository.save(trainingSession);
  }

  @Override
  public Optional<TrainingSession> findWithCurriculumTrainingSetsById(Long id) {
    return this.trainingSessionRepository.findWithCurriculumTrainingSetsById(id);
  }

  @Override
  public CompletableFuture<List<TrainingSession>> findByGroupIdAndStartedAtBetween(
      Long groupId, Month month) {
    Long startOfMonth =
        DurationConverterUtil.getStartOfMonth(
            month, SecurityUserDetailsUtil.PRINCIPAL.getTimeZone());
    Long endOfMonth =
        DurationConverterUtil.getEndOfMonth(month, SecurityUserDetailsUtil.PRINCIPAL.getTimeZone());

    return CompletableFuture.supplyAsync(
        () ->
            this.trainingSessionRepository.findByGroup_IdAndStartedAtBetween(
                groupId, startOfMonth, endOfMonth),
        this.newVirtualThreadPerTaskExecutor);
  }
}
