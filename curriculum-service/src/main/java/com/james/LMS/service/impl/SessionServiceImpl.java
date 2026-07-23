package com.james.LMS.service.impl;

import com.james.LMS.entity.Session;
import com.james.LMS.repository.SessionRepository;
import com.james.LMS.service.SessionService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
  private final SessionRepository sessionRepository;

  @Override
  public List<Session> findAllByCurriculumId(Long curriculumId) {
    return this.sessionRepository.findAllByCurriculumId(curriculumId);
  }

  @Override
  public Boolean existsByIdAndCurriculumId(Long sessionId, Long curriculumId) {
    return this.sessionRepository.existsByIdAndCurriculum_IdAndIsActiveIsTrue(
        sessionId, curriculumId);
  }

  @Override
  public Optional<Session> findById(Long id) {
    return this.sessionRepository.findById(id);
  }

  @Override
  public Optional<Session> findByIdAndCurriculumId(Long sessionId, Long curriculumId) {
    return this.sessionRepository.findByIdAndCurriculum_Id(sessionId, curriculumId);
  }

  @Override
  public List<Session> findAllSessionAndFetchVideosAndExamsByCurriculumId(Long curriculumId) {
    return this.sessionRepository.findAllSessionAndFetchVideosAndExamsByCurriculumId(curriculumId);
  }

  public CompletableFuture<List<Session>> findSessionsFutureByCurriculumId(Long curriculumId) {
    return CompletableFuture.supplyAsync(() -> this.findAllByCurriculumId(curriculumId));
  }
}
