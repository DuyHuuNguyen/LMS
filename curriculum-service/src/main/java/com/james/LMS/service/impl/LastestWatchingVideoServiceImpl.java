package com.james.LMS.service.impl;

import com.james.LMS.dto.ActiveCurrentSessionDTO;
import com.james.LMS.entity.LastestWatchingVideo;
import com.james.LMS.repository.LastestWatchingVideoRepository;
import com.james.LMS.service.LastestWatchingVideoService;
import java.util.Optional;
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

  @Override
  public Optional<LastestWatchingVideo> findById(Long id) {
    return this.lastestWatchingVideoRepository.findByIdAndActive(id);
  }

  @Override
  public Optional<LastestWatchingVideo> findByVideoId(Long videoId) {
    return this.lastestWatchingVideoRepository.findByVideoId(videoId);
  }

  @Override
  public  Optional<ActiveCurrentSessionDTO> findByUserIdAndCurriculumId(Long userId, Long curriculumId) {
    return this.lastestWatchingVideoRepository.findByUserIdAndCurriculumId(userId,curriculumId);
  }
}
