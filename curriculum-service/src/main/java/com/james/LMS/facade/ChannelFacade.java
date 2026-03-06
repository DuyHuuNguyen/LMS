package com.james.LMS.facade;

import com.james.LMS.request.ChannelCurriculumsRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.ChannelCurriculumResponse;
import com.james.LMS.response.PaginationResponse;

public interface ChannelFacade {
  BaseResponse<PaginationResponse<ChannelCurriculumResponse>> findAllCurriculumsInChannel(
      ChannelCurriculumsRequest request);

  Long findChannelIdByUserId(Long userId);
}
