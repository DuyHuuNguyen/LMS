package com.james.LMS.service;

import com.james.LMS.dto.CurriculumAuditLearningProgressDTO;
import com.james.LMS.entity.LearningProgress;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface LearningProgressService {
  void save(LearningProgress learningProgress);

  Optional<LearningProgress> findByUserIdAndCurriculumId(Long userId, Long curriculumId);

  Slice<LearningProgress> findAllByUserCurriculumUserIdAndIsActiveTrue(
      Long userId, Pageable pageable);

  Slice<CurriculumAuditLearningProgressDTO> findAllByUserId(Long userId, Pageable pageable);
}
