package com.james.LMS.service.impl;

import com.james.LMS.repository.PostRepository;
import com.james.LMS.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {
  private final PostRepository postRepository;
}
