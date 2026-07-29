package com.james.LMS.service.impl;

import com.james.LMS.entity.UserCurriculum;
import com.james.LMS.repository.UserCurriculumRepository;
import com.james.LMS.service.UserCurriculumService;
import java.util.Optional;
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

  @Override
  public Optional<UserCurriculum> findByUserIdAndCurriculumIdAndVideoId(
      Long videoId, Long userId, Long curriculumId) {
    return this.curriculumRepository.findByUserIdAndCurriculumIdAndVideoId(
        videoId, userId, curriculumId);
  }

  @Override
  public Optional<UserCurriculum> findByUserIdAndCurriculumIdAndExamId(
      Long examId, Long userId, Long curriculumId) {
    return this.curriculumRepository.findByUserIdAndCurriculumIdAndExamId(
        examId, userId, curriculumId);
  }
}
