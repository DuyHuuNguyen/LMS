package com.james.LMS.service;

import com.james.LMS.entity.Session;
import java.util.List;
import java.util.Optional;

public interface SessionService {
  List<Session> findAllByCurriculumId(Long curriculumId);

  Boolean existsByIdAndCurriculumId(Long sessionId, Long curriculumId);

  Optional<Session> findById(Long id);

  Optional<Session> findByIdAndCurriculumId(Long sessionId, Long curriculumId);

  List<Session> findAllSessionAndFetchVideosAndExamsByCurriculumId(Long curriculumId);
}
