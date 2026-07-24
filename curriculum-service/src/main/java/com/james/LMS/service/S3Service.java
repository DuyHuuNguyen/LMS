package com.james.LMS.service;

import com.james.LMS.dto.AbortMultiPartUploadDTO;
import com.james.LMS.dto.CompletedMultiPartDTO;
import com.james.LMS.dto.UploadingPartDTO;

public interface S3Service {
  String createMultipartUpload(String bucket, String objectKey);

  String uploadPart(UploadingPartDTO uploadingPartDTO);

  void completeMultipartUpload(CompletedMultiPartDTO completedMultiPartDTO);

  void abortMultipartUpload(AbortMultiPartUploadDTO abortMultiPartUploadDTO);

  void saveUploadSession(String uploadId, String objectKey);

  void saveUploadedPart(String uploadId, int partNumber, String eTag);
}
