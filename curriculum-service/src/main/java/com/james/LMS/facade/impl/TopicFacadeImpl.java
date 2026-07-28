package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.TopicDTO;
import com.james.LMS.entity.Topic;
import com.james.LMS.entity.UserTopic;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.facade.TopicFacade;
import com.james.LMS.request.AddPersonalFollowedTopicsRequest;
import com.james.LMS.request.AllTopicRequest;
import com.james.LMS.request.PersonalFollowedTopicsRequest;
import com.james.LMS.request.UnfollowedTopicsRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.PaginationResponse;
import com.james.LMS.response.TopicResponse;
import com.james.LMS.service.TopicService;
import com.james.LMS.service.UserTopicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicFacadeImpl implements TopicFacade {
  private final TopicService topicService;
  private final UserTopicService userTopicService;

  @Override
  public BaseResponse<PaginationResponse<TopicResponse>> findPersonalFollowedTopics(
      PersonalFollowedTopicsRequest personalFollowedTopicsRequest) {

    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Pageable pageable =
        PageRequest.of(
            personalFollowedTopicsRequest.getCurrentPage(),
            personalFollowedTopicsRequest.getPageSize());
    Page<TopicDTO> topicDTOPage = this.topicService.findAllByUserId(principal.getId(), pageable);

    List<TopicResponse> topicResponses =
        topicDTOPage
            .get()
            .map(
                topicDTO ->
                    TopicResponse.builder().id(topicDTO.getId()).name(topicDTO.getName()).build())
            .toList();

    return BaseResponse.build(
        PaginationResponse.<TopicResponse>builder()
            .totalPages(topicDTOPage.getTotalPages())
            .totalElements(topicDTOPage.getNumberOfElements())
            .currentPage(personalFollowedTopicsRequest.getCurrentPage())
            .data(topicResponses)
            .build(),
        true);
  }

  @Override
  @Transactional
  public BaseResponse<Void> followTopics(
      AddPersonalFollowedTopicsRequest addPersonalFollowedTopicsRequest) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    for (long topicId : addPersonalFollowedTopicsRequest.getTopicIds()) {
      boolean isAlreadyExistsFollowTopic =
          this.userTopicService.existsByUserIdAndTopicId(principal.getId(), topicId);
      if (isAlreadyExistsFollowTopic) {
        log.warn("User followed topic id {}", topicId);
        continue;
      }
      try {
        Topic topic =
            this.topicService
                .findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_TOPIC_NOT_FOUND));
        UserTopic userTopic = UserTopic.builder().userId(principal.getId()).build();
        userTopic.addTopic(topic);
        userTopicService.save(userTopic);
      } catch (EntityNotFoundException e) {
        log.warn("User follow topic not found id{}", topicId);
      }
    }

    return BaseResponse.ok();
  }

  @Override
  @Transactional
  public BaseResponse<Void> unfollowTopics(UnfollowedTopicsRequest unfollowedTopicsRequest) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    int unfollowTopics =
        this.userTopicService.unfollowTopic(
            unfollowedTopicsRequest.getUnfollowTopicIds(), principal.getId());
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<PaginationResponse<TopicResponse>> findAll(AllTopicRequest allTopicRequest) {
    Pageable pageable =
        PageRequest.of(allTopicRequest.getCurrentPage() - 1, allTopicRequest.getPageSize());
    Page<TopicDTO> topicDTOPage = this.topicService.findAll(pageable);
    List<TopicResponse> topicResponses =
        topicDTOPage
            .get()
            .map(
                topicDTO ->
                    TopicResponse.builder().id(topicDTO.getId()).name(topicDTO.getName()).build())
            .toList();

    return BaseResponse.build(
        PaginationResponse.<TopicResponse>builder()
            .totalPages(topicDTOPage.getTotalPages())
            .totalElements(topicDTOPage.getNumberOfElements())
            .currentPage(allTopicRequest.getCurrentPage())
            .data(topicResponses)
            .build(),
        true);
  }
}
