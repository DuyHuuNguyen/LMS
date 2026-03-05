package com.james.LMS.facade;

import com.james.LMS.request.UpdateSessionVideoRequest;
import com.james.LMS.request.VideoStreamingPresignRequest;
import com.james.LMS.request.VideoUploadingPresignUrlRequest;
import com.james.LMS.response.BaseResponse;

public interface VideoFacade {
  BaseResponse<String> generateVideoStreamingPresignUrl(VideoStreamingPresignRequest request);

  BaseResponse<String> generateVideoUploadPresignUrl(VideoUploadingPresignUrlRequest request);

  BaseResponse<Void> changeSessionVideo(UpdateSessionVideoRequest request);
}
