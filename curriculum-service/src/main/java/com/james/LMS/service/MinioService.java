package com.james.LMS.service;

public interface MinioService {

  String generatePresignedVideoUploadUrl(String fileName);

  String generatePresignedThumbnailUrl(String fileName);

  String generatePresignedVideoStreamingUrl(String fileName, Integer durationOfVideo);

  Boolean isExistFile(String bucket, String fileUrl);
}
