package com.james.LMS.service;

import com.james.LMS.dto.PresignURLAndPauseDTO;

public interface MinioService {

  void createBucket(String bucketName);

  String generatePresignedVideoUploadUrl(String fileName);

  String generatePresignedThumbnailUrl(String fileName);

  String generatePresignedVideoStreamingUrl(String fileName, Integer durationOfVideo);

  Boolean isExistFile(String bucket, String fileUrl);

  PresignURLAndPauseDTO generatePresignedVideoStreamingUrl(String fileName, Integer durationOfVideo, Long pausedAt);
}
