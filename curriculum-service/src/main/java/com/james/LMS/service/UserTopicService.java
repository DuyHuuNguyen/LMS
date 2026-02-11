package com.james.LMS.service;

import java.util.List;

public interface UserTopicService {
  List<Long> findAllTopicIdsByUserId(Long userId);
}
