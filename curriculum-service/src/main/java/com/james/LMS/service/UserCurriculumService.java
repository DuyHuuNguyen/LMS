package com.james.LMS.service;

public interface UserCurriculumService {
  Boolean existsByUserIdAndCurriculumId(Long userId, Long curriculumId);
}
