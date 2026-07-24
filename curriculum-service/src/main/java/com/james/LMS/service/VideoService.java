package com.james.LMS.service;

import com.james.LMS.dto.VideoDTO;
import com.james.LMS.entity.Video;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface VideoService {
  List<Video> findAllBySessionIds(List<Long> sessionIds);

  List<VideoDTO> findVideoDTOBySessionIds(List<Long> sessionIds);

  Optional<Video> findById(Long id);

  Integer findDurationById(Long id);

  void save(Video video);

  Optional<Video> findByIdentifyCode(String identifyCode);

  Optional<Video> findVideoAndFetchSessionById(Long id);

  Optional<Video> findByIdAndIsActiveIsTrue(Long id);

  Optional<String> generatePresignUrlToWatchVideo(Long id);

  CompletableFuture<Video> findCompletableFutureVideoAndFetchSessionById(Long videoId);
}
