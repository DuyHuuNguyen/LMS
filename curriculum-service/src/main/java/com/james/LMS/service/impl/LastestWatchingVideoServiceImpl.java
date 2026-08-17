package com.james.LMS.service.impl;

import com.james.LMS.dto.ActiveCurrentSessionDTO;
import com.james.LMS.entity.LastestWatchingVideo;
import com.james.LMS.repository.LastestWatchingVideoRepository;
import com.james.LMS.service.LastestWatchingVideoService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LastestWatchingVideoServiceImpl implements LastestWatchingVideoService {
  private final LastestWatchingVideoRepository lastestWatchingVideoRepository;

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void save(LastestWatchingVideo lastestWatchingVideo) {
    this.lastestWatchingVideoRepository.save(lastestWatchingVideo);
  }

  @Override
  public Optional<LastestWatchingVideo> findById(Long id) {
    return this.lastestWatchingVideoRepository.findByIdAndActive(id);
  }

  @Override
  public Optional<LastestWatchingVideo> findByVideoId(Long videoId) {
    return this.lastestWatchingVideoRepository.findByVideoId(videoId);
  }

  @Override
  public Optional<ActiveCurrentSessionDTO> findByUserIdAndCurriculumId(
      Long userId, Long curriculumId) {
    return this.lastestWatchingVideoRepository.findByUserIdAndCurriculumId(userId, curriculumId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void disableActiveCurrentWatchSessionContent(Long userId, Long curriculumId) {
    this.lastestWatchingVideoRepository.disableActiveCurrentWatchSessionContent(
        userId, curriculumId);
  }
}
