package com.james.LMS.service;

import com.james.LMS.dto.TopicDTO;
import com.james.LMS.entity.Topic;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {
  List<TopicDTO> findAllTopicDTOByCurriculumId(Long curriculumId);

  List<Long> findAllTopicIdsByUserId(Long userId, Pageable pageable);

  Page<TopicDTO> findAll(Pageable pageable);

  Boolean existsById(Long id);

  Page<TopicDTO> findAllByUserId(Long userId, Pageable pageable);

  Optional<Topic> findById(Long id);
}
