package com.james.LMS.facade;

import com.james.LMS.request.AddPersonalFollowedTopicsRequest;
import com.james.LMS.request.AllTopicRequest;
import com.james.LMS.request.PersonalFollowedTopicsRequest;
import com.james.LMS.request.UnfollowedTopicsRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.PaginationResponse;
import com.james.LMS.response.TopicResponse;
import jakarta.validation.constraints.NotNull;

public interface TopicFacade {
  BaseResponse<PaginationResponse<TopicResponse>> findPersonalFollowedTopics(
      PersonalFollowedTopicsRequest personalFlowedTopicsRequest);

  BaseResponse<Void> followTopics(AddPersonalFollowedTopicsRequest addPersonalFlowedTopicsRequest);

  BaseResponse<Void> unfollowTopics(UnfollowedTopicsRequest unfollowedTopicsRequest);

  BaseResponse<PaginationResponse<TopicResponse>> findAll(@NotNull AllTopicRequest allTopicRequest);
}
