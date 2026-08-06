package com.james.LMS.facade;

import com.james.LMS.request.DashBoardRequest;
import com.james.LMS.request.UpsertTrainingSessionRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.DashBoardResponse;

public interface GroupFacade {
  BaseResponse<DashBoardResponse> dashBoard(DashBoardRequest request);

  BaseResponse<Void> createTrainingSession(UpsertTrainingSessionRequest request);
}
