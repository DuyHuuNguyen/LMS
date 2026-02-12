package com.james.LMS.service.impl;

import com.james.LMS.dto.TopicDTO;
import com.james.LMS.repository.TopicRepository;
import com.james.LMS.service.TopicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
  private final TopicRepository topicRepository;

  @Override
  public List<TopicDTO> findAllTopicDTOByCurriculumId(Long curriculumId) {
    return this.topicRepository.findAllTopicDTOByCurriculumId(curriculumId);
  }

  @Override
  public List<Long> findAllTopicIdsByUserId(Long userId, Pageable pageable) {
    return this.topicRepository.findAllTopicIdsByUserId(userId, pageable);
  }

  @Override
  public Page<TopicDTO> findAll(Pageable pageable) {
    return this.topicRepository.findAllTopicDTOs(pageable);
  }
}
