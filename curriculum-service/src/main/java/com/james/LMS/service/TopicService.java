package com.james.LMS.service;

import com.james.LMS.dto.TopicDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TopicService {
  List<TopicDTO> findAllTopicDTOByCurriculumId(Long curriculumId);

  List<Long> findAllTopicIdsByUserId(Long userId, Pageable pageable);
}
