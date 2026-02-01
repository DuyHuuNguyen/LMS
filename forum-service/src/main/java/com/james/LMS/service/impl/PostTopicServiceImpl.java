package com.james.LMS.service.impl;

import com.james.LMS.repository.PostTopicRepository;
import com.james.LMS.service.PostTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostTopicServiceImpl implements PostTopicService {
    private final PostTopicRepository postTopicRepository;
}
