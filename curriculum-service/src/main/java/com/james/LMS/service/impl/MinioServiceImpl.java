package com.james.LMS.service.impl;

import com.james.LMS.config.MinioConfig;
import com.james.LMS.service.MinioService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
  private final MinioClient minioClient;
  private final MinioConfig config;
  private final int EXPIRED_AT = 60 * 10;

  @Override
  @SneakyThrows
  public String generatePresignedVideoUploadUrl(String fileName) {
    return minioClient.getPresignedObjectUrl(
        GetPresignedObjectUrlArgs.builder()
            .method(Method.PUT)
            .bucket(config.getVideoBucket())
            .object(fileName)
            .expiry(EXPIRED_AT)
            .build());
  }

  @Override
  @SneakyThrows
  public String generatePresignedThumbnailUrl(String fileName) {
    return minioClient.getPresignedObjectUrl(
        GetPresignedObjectUrlArgs.builder()
            .method(Method.GET)
            .bucket(config.getThumbnailBucket())
            .object(fileName)
            .expiry(EXPIRED_AT)
            .build());
  }

  @Override
  @SneakyThrows
  public String generatePresignedVideoStreamingUrl(String fileName, Integer durationOfVideo) {
    return minioClient.getPresignedObjectUrl(
        GetPresignedObjectUrlArgs.builder()
            .method(Method.GET)
            .bucket(config.getVideoBucket())
            .object(fileName)
            .expiry(durationOfVideo)
            .build());
  }
}
