package com.james.LMS.service;

import com.james.LMS.dto.TopicDTO;
import java.util.List;

public interface TopicService {
  List<TopicDTO> findAllTopicDTOByCurriculumId(Long curriculumId);
}
