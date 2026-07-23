package com.james.LMS.facade.impl;

import com.james.LMS.config.MinioConfig;
import com.james.LMS.dto.CompletedMultiPartDTO;
import com.james.LMS.dto.UploadingPartDTO;
import com.james.LMS.entity.FilePart;
import com.james.LMS.entity.UploadingSession;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.enums.UploadingSessionStatus;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.UploadFileException;
import com.james.LMS.facade.UploadingLargeFileFacade;
import com.james.LMS.request.InitialUploadLargeFileSessionRequest;
import com.james.LMS.request.UploadFileChunkRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.InitialUploadLargeFileSessionResponse;
import com.james.LMS.service.FilePartService;
import com.james.LMS.service.S3Service;
import com.james.LMS.service.UploadingSessionService;
import com.james.LMS.util.IdentifyCodeOfVideoUtil;
import com.james.LMS.util.SecurityUserDetailsUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadingLargeFileFacadeImpl implements UploadingLargeFileFacade {

  private final S3Service s3Service;
  private final UploadingSessionService uploadingSessionService;
  private final FilePartService filePartService;
  private final MinioConfig minioConfig;
  private final Long PART_SIZE_BYTES = 16 * 1024 *1024L;

  @Override
  @Transactional
  public BaseResponse<InitialUploadLargeFileSessionResponse> initialUploadLargeFileSession(
      InitialUploadLargeFileSessionRequest request) {

    String objectKey =
        IdentifyCodeOfVideoUtil.genVideoIdentifyCode(
            SecurityUserDetailsUtil.PRINCIPAL.getUsername(), request.getFilename());
    String s3UploadId = s3Service.createMultipartUpload(minioConfig.getVideoBucket(), objectKey);

    int totalParts = Math.toIntExact(request.getFileSizeBytes() / PART_SIZE_BYTES);

    UploadingSession uploadingSession =
        UploadingSession.builder()
            .partSize(PART_SIZE_BYTES)
            .s3UploadId(s3UploadId)
            .bucket(minioConfig.getVideoBucket())
            .objectKey(objectKey)
            .status(UploadingSessionStatus.PENDING)
            .totalParts(totalParts)
            .build();

    long id = this.uploadingSessionService.saveAndFetchId(uploadingSession);

    return BaseResponse.build(
        InitialUploadLargeFileSessionResponse.builder()
            .s3uploadId(s3UploadId)
            .totalParts(totalParts)
            .partSize(PART_SIZE_BYTES)
            .uploadingSessionId(id)
            .build(),
        true);
  }

  /**
   * May be use queue to save entity in order to enhance performance !!
   * @param request
   * @return
   */
  @SneakyThrows
  @Override
  @Transactional
  public BaseResponse<Void> uploadChunkFile(UploadFileChunkRequest request) {

    boolean isTheFirstPart = request.getPartNumber().equals(1);
    boolean isVerifyNextPartNumber = isTheFirstPart || this.uploadingSessionService.verifyNextPartNumberBySessionId(request.getUploadingSessionId(), request.getPartNumber());

    if (!isVerifyNextPartNumber)
      throw new EntityNotFoundException(ErrorCode.THE_FILE_CHUNKS_MUST_BE_UPLOADED_IN_SEQUENTIAL_ORDER);


    UploadingSession uploadingSession = this.uploadingSessionService
            .findById(request.getUploadingSessionId()).orElseThrow(()-> new EntityNotFoundException(ErrorCode.UPLOADING_SESSION_NOT_FOUND));

    UploadingPartDTO uploadingPartDTO = UploadingPartDTO.builder()
            .bucket(uploadingSession.getBucket())
            .objectKey(uploadingSession.getObjectKey())
            .uploadId(uploadingSession.getS3UploadId())
            .partNumber(request.getPartNumber())
            .inputStream(request.getChunk().getInputStream())
            .contentLength(request.getChunk().getSize())
            .build();

    String etag = s3Service.uploadPart(uploadingPartDTO);

    FilePart filePart = FilePart.builder()
            .partNumber(request.getPartNumber())
            .contentLength(request.getChunk().getSize())
            .etag(etag)
            .uploadingSession(uploadingSession)
            .build();

    this.filePartService.save(filePart);

    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<Void> completeUploadFile(Long uploadingSessionId) {
    CompletedMultiPartDTO completedMultiPartDTO = this.uploadingSessionService.findCompletedParts(uploadingSessionId);
    try {
      this.s3Service.completeMultipartUpload(completedMultiPartDTO);
    } catch (AwsServiceException | SdkClientException e) {
      log.error(e.getMessage());
    }
    return BaseResponse.ok();
  }
}
