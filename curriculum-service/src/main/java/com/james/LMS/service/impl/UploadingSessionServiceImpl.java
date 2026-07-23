package com.james.LMS.service.impl;

import com.james.LMS.dto.CompletedMultiPartDTO;
import com.james.LMS.entity.UploadingSession;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.repository.UploadingSessionRepository;
import com.james.LMS.service.UploadingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.CompletedPart;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadingSessionServiceImpl implements UploadingSessionService {

  private final UploadingSessionRepository uploadingSessionRepository;

  @Override
  public void save(UploadingSession uploadingSession) {
    this.uploadingSessionRepository.save(uploadingSession);
  }

  @Override
  public Long saveAndFetchId(UploadingSession uploadingSession) {
    UploadingSession uploadingSessionSaved = this.uploadingSessionRepository.save(uploadingSession);
    return uploadingSessionSaved.getId();
  }

  @Override
  public Optional<UploadingSession> findById(Long id) {
    return this.uploadingSessionRepository.findById(id);
  }

  @Override
  public Boolean verifyNextPartNumberBySessionId(Long id,Integer nextPartNumber) {
    return this.uploadingSessionRepository.verifyNextPartNumberBySessionId(id,nextPartNumber);
  }

  public CompletedMultiPartDTO  findCompletedParts(Long id){
    UploadingSession uploadingSession = this.findById(id).orElseThrow(()-> new EntityNotFoundException(ErrorCode.UPLOADING_SESSION_NOT_FOUND));
    List<CompletedPart>  completedParts = this.uploadingSessionRepository.findAllById(id).stream().map(
                    etagPartNumberDTO ->
                            CompletedPart.builder()
                                    .partNumber(etagPartNumberDTO.getPartNumber())
                                    .eTag(etagPartNumberDTO.getEtag())
                                    .build())
            .toList();

    if (completedParts.isEmpty())
      throw new EntityNotFoundException(ErrorCode.UPLOADING_SESSION_NOT_FOUND);

     return CompletedMultiPartDTO.builder()
            .bucket(uploadingSession.getBucket())
             .objectKey(uploadingSession.getObjectKey())
             .uploadId(uploadingSession.getS3UploadId())
             .completedParts(completedParts)
             .build();
  }

}
