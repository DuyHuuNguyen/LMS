package com.james.LMS.service.impl;

import com.james.LMS.dto.VideoDTO;
import com.james.LMS.entity.Video;
import com.james.LMS.repository.VideoRepository;
import com.james.LMS.service.VideoService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
  private final VideoRepository videoRepository;

  @Override
  public List<Video> findAllBySessionIds(List<Long> sessionIds) {
    return this.videoRepository.findAllBySessionIds(sessionIds);
  }

  @Override
  public List<VideoDTO> findVideoDTOBySessionIds(List<Long> sessionIds) {
    return this.videoRepository.findVideoDTOBySessionId(sessionIds);
  }

  @Override
  public Optional<Video> findById(Long id) {
    return this.videoRepository.findById(id);
  }

  @Override
  public Integer findDurationById(Long id) {
    return this.videoRepository.findDurationById(id);
  }

  @Override
  public void save(Video video) {
    this.videoRepository.save(video);
  }

  @Override
  public Optional<Video> findByIdentifyCode(String identifyCode) {
    return this.videoRepository.findByIdentifyCode(identifyCode);
  }

  @Override
  public Optional<Video> findVideoAndFetchSessionById(Long id) {
    return this.videoRepository.findVideoAndFetchSessionById(id);
  }

  @Override
  public Optional<Video> findByIdAndIsActiveIsTrue(Long id) {
    return this.videoRepository.findByIdAndIsActiveIsTrue(id);
  }
}
