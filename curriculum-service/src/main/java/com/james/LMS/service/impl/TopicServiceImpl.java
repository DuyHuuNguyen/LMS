package com.james.LMS.service.impl;

import com.james.LMS.dto.TopicDTO;
import com.james.LMS.entity.Topic;
import com.james.LMS.repository.TopicRepository;
import com.james.LMS.service.TopicService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
  private final TopicRepository topicRepository;

  @Qualifier("VirtualThreadPerTaskExecutor")
  private final AsyncTaskExecutor asyncTaskExecutor;

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

  @Override
  public Boolean existsById(Long id) {
    return this.topicRepository.existsById(id);
  }

  @Override
  public Page<TopicDTO> findAllByUserId(Long userId, Pageable pageable) {
    return this.topicRepository.findAllByUserId(userId, pageable);
  }

  @Override
  public Optional<Topic> findById(Long id) {
    return this.topicRepository.findById(id);
  }

  @Override
  public CompletableFuture<List<TopicDTO>> findTopicsFutureByCurriculumId(Long curriculumId) {
    return CompletableFuture.supplyAsync(
        () -> this.findAllTopicDTOByCurriculumId(curriculumId), this.asyncTaskExecutor);
  }
}
