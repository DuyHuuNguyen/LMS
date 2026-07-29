package com.james.LMS.service.impl;

import com.james.LMS.entity.LearningProgress;
import com.james.LMS.repository.LearningProgressRepository;
import com.james.LMS.service.LearningProgressService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LearningProgressServiceImpl implements LearningProgressService {
  private final LearningProgressRepository learningProgressRepository;

  @Override
  public void save(LearningProgress learningProgress) {
    this.learningProgressRepository.save(learningProgress);
  }

  @Override
  public Optional<LearningProgress> findByUserIdAndCurriculumId(Long userId, Long curriculumId) {
    return this.learningProgressRepository.findByUserIdAndCurriculumId(userId, curriculumId);
  }
}
