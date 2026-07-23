package com.james.LMS.service.impl;

import com.james.LMS.dto.CompletedMultiPartDTO;
import com.james.LMS.dto.UploadingPartDTO;
import com.james.LMS.service.S3Service;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
  private final S3Client s3Client;

  @Override
  public String createMultipartUpload(String bucket, String objectKey) {
    CreateMultipartUploadRequest request =
        CreateMultipartUploadRequest.builder().bucket(bucket).key(objectKey).build();

    CreateMultipartUploadResponse response = s3Client.createMultipartUpload(request);

    String uploadId = response.uploadId();

    return uploadId;
  }

  @Override
  public String uploadPart(UploadingPartDTO uploadingPartDTO) {

    UploadPartRequest request =
        UploadPartRequest.builder()
            .bucket(uploadingPartDTO.bucket())
            .key(uploadingPartDTO.objectKey())
            .uploadId(uploadingPartDTO.uploadId())
            .partNumber(uploadingPartDTO.partNumber())
            .build();

    UploadPartResponse response =
        s3Client.uploadPart(request, RequestBody.fromInputStream(uploadingPartDTO.inputStream(), uploadingPartDTO.contentLength()));

    return response.eTag();
  }

  public List<Part> listUploadedParts(String bucket, String objectKey, String uploadId) {

    ListPartsRequest request =
        ListPartsRequest.builder().bucket(bucket).key(objectKey).uploadId(uploadId).build();

    ListPartsResponse response = s3Client.listParts(request);

    return response.parts();
  }

  @Override
  public void completeMultipartUpload(CompletedMultiPartDTO completedMultiPartDTO) {


    CompletedMultipartUpload completedUpload =
        CompletedMultipartUpload.builder().parts(completedMultiPartDTO.completedParts()).build();

    CompleteMultipartUploadRequest request =
        CompleteMultipartUploadRequest.builder()
            .bucket(completedMultiPartDTO.bucket())
            .key(completedMultiPartDTO.objectKey())
            .uploadId(completedMultiPartDTO.uploadId())
            .multipartUpload(completedUpload)
            .build();

    s3Client.completeMultipartUpload(request);
  }

  @Override
  public void abortMultipartUpload(String bucket, String objectKey, String uploadId) {

    AbortMultipartUploadRequest request =
        AbortMultipartUploadRequest.builder()
            .bucket(bucket)
            .key(objectKey)
            .uploadId(uploadId)
            .build();

    s3Client.abortMultipartUpload(request);
  }

  @Override
  public void saveUploadSession(String uploadId, String objectKey) {}

  @Override
  public void saveUploadedPart(String uploadId, int partNumber, String eTag) {}
}
