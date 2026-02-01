package com.james.LMS.service.impl;

import com.james.LMS.repository.PostRepository;
import com.james.LMS.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
}
