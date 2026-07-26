package com.james.LMS.facade;

import com.james.LMS.request.StoppedWatchingContentRequest;
import com.james.LMS.response.BaseResponse;

public interface TrackingBehaviorFacade {
  BaseResponse<Void> trackingStoppedWatchingContent(StoppedWatchingContentRequest request);
}
