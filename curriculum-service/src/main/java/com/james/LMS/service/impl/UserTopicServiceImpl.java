package com.james.LMS.service.impl;

import com.james.LMS.entity.UserTopic;
import com.james.LMS.repository.UserTopicRepository;
import com.james.LMS.service.UserTopicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTopicServiceImpl implements UserTopicService {
  private final UserTopicRepository userTopicRepository;

  @Override
  public List<Long> findAllTopicIdsByUserId(Long userId) {
    return this.userTopicRepository.findAllTopicIdsByUserId(userId);
  }

  @Override
  public void save(UserTopic userTopic) {
    this.userTopicRepository.save(userTopic);
  }

  @Override
  public Boolean existsByUserIdAndTopicId(Long userId, Long topicId) {
    return this.userTopicRepository.existsByUserIdAndTopic_IdAndIsActiveIsTrue(userId, topicId);
  }

  @Override
  public Integer unfollowTopic(List<Long> topicIds, Long userId) {
    return this.userTopicRepository.unfollowTopics(topicIds, userId);
  }
}
