package com.james.LMS.service;

import com.james.LMS.entity.UserTopic;
import java.util.List;

public interface UserTopicService {
  List<Long> findAllTopicIdsByUserId(Long userId);

  void save(UserTopic userTopic);

  Boolean existsByUserIdAndTopicId(Long userId, Long topicId);

  Integer unfollowTopic(List<Long> topicId, Long userId);
}
