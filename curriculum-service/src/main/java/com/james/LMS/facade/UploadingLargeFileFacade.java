package com.james.LMS.facade;

import com.james.LMS.request.InitialUploadLargeFileSessionRequest;
import com.james.LMS.request.UploadFileChunkRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.InitialUploadLargeFileSessionResponse;

public interface UploadingLargeFileFacade {
  BaseResponse<InitialUploadLargeFileSessionResponse> initialUploadLargeFileSession(
      InitialUploadLargeFileSessionRequest request);

  BaseResponse<Void> uploadChunkFile(UploadFileChunkRequest request);

  BaseResponse<Void> completeUploadFile(Long uploadingSessionId);


}
