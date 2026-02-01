package com.james.LMS.service.impl;

import com.james.LMS.repository.TopicRepository;
import com.james.LMS.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
  private final TopicRepository topicRepository;
}
