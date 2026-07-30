package com.james.LMS.service.impl;

import com.james.LMS.dto.CurriculumAuditLearningProgressDTO;
import com.james.LMS.entity.LearningProgress;
import com.james.LMS.repository.LearningProgressRepository;
import com.james.LMS.service.LearningProgressService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

  @Override
  public Slice<LearningProgress> findAllByUserCurriculumUserIdAndIsActiveTrue(
      Long userId, Pageable pageable) {
    return this.learningProgressRepository.findAllByUserCurriculumUserIdAndIsActiveTrue(
        userId, pageable);
  }

  @Override
  public Slice<CurriculumAuditLearningProgressDTO> findAllByUserId(Long userId, Pageable pageable) {
    return this.learningProgressRepository.findAllByUserId(userId, pageable);
  }
}
