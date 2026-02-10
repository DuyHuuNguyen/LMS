package com.james.LMS.service;

import com.james.LMS.dto.CurriculumDTO;
import com.james.LMS.entity.Curriculum;
import java.util.List;
import java.util.Optional;

public interface CurriculumService {
  Optional<Curriculum> findById(Long id);

  List<CurriculumDTO> findAllInTopicOfUser(
      List<Long> topicIdsOfUser, Integer currentPage, Integer limit);
}
