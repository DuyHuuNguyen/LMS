package com.james.LMS.service;

import com.james.LMS.dto.CompletedMultiPartDTO;
import com.james.LMS.dto.UploadingPartDTO;

import java.io.InputStream;
import java.util.Map;

public interface S3Service {
  String createMultipartUpload(String bucket, String objectKey);

  String uploadPart(UploadingPartDTO uploadingPartDTO);

  void completeMultipartUpload(CompletedMultiPartDTO completedMultiPartDTO);

  void abortMultipartUpload(String bucket, String objectKey, String uploadId);

  void saveUploadSession(String uploadId, String objectKey);

  void saveUploadedPart(String uploadId, int partNumber, String eTag);
}
