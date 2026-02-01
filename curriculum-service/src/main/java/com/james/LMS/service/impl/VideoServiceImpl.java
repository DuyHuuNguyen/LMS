package com.james.LMS.service.impl;

import com.james.LMS.repository.VideoRepository;
import com.james.LMS.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
  private final VideoRepository videoRepository;
}
