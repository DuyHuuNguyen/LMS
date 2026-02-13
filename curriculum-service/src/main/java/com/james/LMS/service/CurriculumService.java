package com.james.LMS.service;

import com.james.LMS.dto.CurriculumDTO;
import com.james.LMS.entity.Curriculum;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurriculumService {
  Optional<Curriculum> findById(Long id);

  List<CurriculumDTO> findAllInTopicOfUser(
      List<Long> topicIdsOfUser, Integer currentPage, Integer limit);

  Page<CurriculumDTO> findAllCurriculumsByFollowedTopicIdsOfUser(
      List<Long> followedTopicIds, Pageable pageable);

  Page<CurriculumDTO> findAllCurriculumByTopicId(Long topicId, Pageable pageable);
}
