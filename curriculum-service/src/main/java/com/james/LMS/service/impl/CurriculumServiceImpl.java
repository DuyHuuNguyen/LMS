package com.james.LMS.service.impl;

import com.james.LMS.dto.CurriculumDTO;
import com.james.LMS.entity.Curriculum;
import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.service.CurriculumService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumServiceImpl implements CurriculumService {
  private final CurriculumRepository curriculumRepository;

  @Override
  public Optional<Curriculum> findById(Long id) {
    return this.curriculumRepository.findById(id);
  }

  @Override
  public List<CurriculumDTO> findAllInTopicOfUser(
      List<Long> topicIdsOfUser, Integer currentPage, Integer limit) {
    return List.of();
  }

  @Override
  public Page<CurriculumDTO> findAllCurriculumsByFollowedTopicIdsOfUser(
      List<Long> followedTopicIds, Pageable pageable) {
    return this.curriculumRepository.findAllCurriculumsByFollowedTopicIdsOfUser(
        followedTopicIds, pageable);
  }
}
