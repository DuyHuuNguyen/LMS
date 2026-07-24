package com.james.LMS.service;

import com.james.LMS.entity.LastestWatchingVideo;

import java.util.Optional;

public interface LastestWatchingVideoService {
  void save(LastestWatchingVideo lastestWatchingVideo);
  Optional<LastestWatchingVideo> findById(Long id);
  Optional<LastestWatchingVideo> findByVideoId(Long id);
}
