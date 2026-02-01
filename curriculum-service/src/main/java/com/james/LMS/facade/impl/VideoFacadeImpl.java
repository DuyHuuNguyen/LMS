package com.james.LMS.facade.impl;

import com.james.LMS.facade.VideoFacade;
import com.james.LMS.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoFacadeImpl implements VideoFacade {
  private final VideoService videoService;
}
