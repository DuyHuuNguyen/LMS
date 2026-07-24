package com.james.LMS.service.impl;

import com.james.LMS.config.MinioConfig;
import com.james.LMS.dto.PresignURLAndPauseDTO;
import com.james.LMS.service.MinioService;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
  private final MinioClient minioClient;
  private final MinioConfig config;
  private final int EXPIRED_AT = 60 * 10;

  @Override
  @SneakyThrows
  public void createBucket(String bucketName) {
    boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    if (!bucketExists) {
      minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
    }
  }

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

  @Override
  public Boolean isExistFile(String bucket, String fileUrl) {
    try {
      minioClient.statObject(StatObjectArgs.builder().bucket(bucket).object(fileUrl).build());
      return true;
    } catch (ErrorResponseException e) {
      if (e.errorResponse().code().equals("NoSuchKey")) {
        return false;
      }
      throw new RuntimeException(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  @SneakyThrows
  public PresignURLAndPauseDTO generatePresignedVideoStreamingUrl(String fileName, Integer durationOfVideo, Long pausedAt){
    return PresignURLAndPauseDTO.builder()
            .presignURL(this.generatePresignedVideoStreamingUrl(fileName,durationOfVideo))
            .pausedAt(pausedAt)
            .build();
  }
}
