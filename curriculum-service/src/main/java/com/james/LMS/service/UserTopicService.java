package com.james.LMS.service;

import com.james.LMS.entity.UserTopic;

import java.util.List;

public interface UserTopicService {
    List<Long> findAllTopicIdsByUserId(Long userId);
}
