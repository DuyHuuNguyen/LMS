package com.james.LMS.facade.impl;

import com.james.LMS.facade.StreamingFacade;
import com.james.LMS.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StreamingFacadeImpl implements StreamingFacade {
  private final VideoService videoService;
}
