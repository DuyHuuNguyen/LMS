package com.james.LMS.service.impl;

import com.james.LMS.repository.UserCurriculumRepository;
import com.james.LMS.service.UserCurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCurriculumServiceImpl implements UserCurriculumService {
  private final UserCurriculumRepository curriculumRepository;

  @Override
  public Boolean existsByUserIdAndCurriculumId(Long userId, Long curriculumId) {
    return this.curriculumRepository.existsUserCurriculumByUserIdAndCurriculum_IdAndIsActiveIsTrue(
        userId, curriculumId);
  }
}
