package com.james.LMS.service.impl;

import com.james.LMS.entity.UserTopic;
import com.james.LMS.repository.UserTopicRepository;
import com.james.LMS.service.UserTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTopicServiceImpl implements UserTopicService {
    private final UserTopicRepository userTopicRepository;

    @Override
    public List<Long> findAllTopicIdsByUserId(Long userId) {
        return this.userTopicRepository.findAllTopicIdsByUserId(userId);
    }
}
