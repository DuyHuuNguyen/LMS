package com.james.LMS.service;

import com.james.LMS.entity.UserCurriculum;
import java.util.Optional;

public interface UserCurriculumService {
  Boolean existsByUserIdAndCurriculumId(Long userId, Long curriculumId);

  Optional<UserCurriculum> findByUserIdAndCurriculumIdAndVideoId(
      Long videoId, Long userId, Long curriculumId);

  Optional<UserCurriculum> findByUserIdAndCurriculumIdAndExamId(
      Long contentId, Long userId, Long curriculumId);
}
