package com.james.LMS.service.impl;

import com.james.LMS.dto.VideoDTO;
import com.james.LMS.entity.Video;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.repository.VideoRepository;
import com.james.LMS.service.VideoService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
  private final VideoRepository videoRepository;
  private final MinioClient minioClient;
  private final ExecutorService newVirtualThreadPerTaskExecutor;

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

  /**
   * The best version for the gen presign url method.
   *
   * @param id
   * @return presignUrl of video in MinIO.
   */
  @Override
  @SneakyThrows
  public Optional<String> generatePresignUrlToWatchVideo(Long id) {
    Video video =
        this.videoRepository
            .findByIdAndFetchBucket(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.VIDEO_METADATA_NOT_FOUND));
    try {

      String presignUrl =
          minioClient.getPresignedObjectUrl(
              GetPresignedObjectUrlArgs.builder()
                  .method(Method.GET)
                  .bucket(video.getBucket().getBucketName())
                  .object(video.getIdentifyCode())
                  .expiry(video.getDurationSeconds())
                  .build());
      return Optional.of(presignUrl);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public CompletableFuture<Video> findCompletableFutureVideoAndFetchSessionById(Long id) {
    return CompletableFuture.supplyAsync(
        () ->
            this.findVideoAndFetchSessionById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.VIDEO_METADATA_NOT_FOUND)),
        this.newVirtualThreadPerTaskExecutor);
  }
}
