package com.james.LMS.service;

import com.james.LMS.dto.CompletedMultiPartDTO;
import com.james.LMS.entity.UploadingSession;
import java.util.Optional;

public interface UploadingSessionService {
  void save(UploadingSession uploadingSession);

  Long saveAndFetchId(UploadingSession uploadingSession);

  Optional<UploadingSession> findById(Long id);

  Boolean verifyNextPartNumberBySessionId(Long id, Integer nextPartNumber);

  CompletedMultiPartDTO findCompletedParts(Long id);
}
