package com.james.LMS.facade;

import com.james.LMS.message.final_lms_message.StopWatchingSessionContentMessage;

public interface TrackingUserStopWatchingSessionContentConsumer {
  void handleStopWatchingVideo(StopWatchingSessionContentMessage message);

  void handleStopExam(StopWatchingSessionContentMessage message);
}
