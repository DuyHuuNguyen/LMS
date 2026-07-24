package com.james.LMS.service.impl;

import com.james.LMS.entity.LastestWatchingVideo;
import com.james.LMS.repository.LastestWatchingVideoRepository;
import com.james.LMS.service.LastestWatchingVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LastestWatchingVideoServiceImpl implements LastestWatchingVideoService {
  private final LastestWatchingVideoRepository lastestWatchingVideoRepository;

  @Override
  public void save(LastestWatchingVideo lastestWatchingVideo) {
    this.lastestWatchingVideoRepository.save(lastestWatchingVideo);
  }
}
