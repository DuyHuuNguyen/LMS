package com.james.LMS.service;

import com.james.LMS.entity.LearningProgress;
import java.util.Optional;

public interface LearningProgressService {
  void save(LearningProgress learningProgress);

  Optional<LearningProgress> findByUserIdAndCurriculumId(Long userId, Long curriculumId);
}
