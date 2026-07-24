package com.james.LMS.facade;

import com.james.LMS.request.UpdateSessionVideoRequest;
import com.james.LMS.request.UpsertMetadataVideoRequest;
import com.james.LMS.request.VideoStreamingPresignRequest;
import com.james.LMS.request.VideoUploadingPresignUrlRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.PresignUrlResponse;

public interface VideoFacade {
  BaseResponse<PresignUrlResponse> generateVideoStreamingPresignUrl(
      VideoStreamingPresignRequest request);

  BaseResponse<String> generateVideoUploadPresignUrl(VideoUploadingPresignUrlRequest request);

  BaseResponse<Void> changeSessionVideo(UpdateSessionVideoRequest request);

  BaseResponse<String> genPresignStreamingVideo(String videoName);

  BaseResponse<Void> createVideoMetadata(UpsertMetadataVideoRequest request);
}
