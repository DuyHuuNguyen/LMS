package com.james.LMS.facade.impl;

import com.james.LMS.dto.CurriculumChannelDTO;
import com.james.LMS.entity.Channel;
import com.james.LMS.facade.ChannelFacade;
import com.james.LMS.request.ChannelCurriculumsRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.ChannelCurriculumResponse;
import com.james.LMS.response.PaginationResponse;
import com.james.LMS.service.ChannelService;
import com.james.LMS.service.CurriculumService;
import com.james.LMS.util.DurationConverterUtil;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelFacadeImpl implements ChannelFacade {
  private final CurriculumService curriculumService;
  private final ChannelService channelService;

  @Override
  public BaseResponse<PaginationResponse<ChannelCurriculumResponse>> findAllCurriculumsInChannel(
      ChannelCurriculumsRequest request) {
    Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize());
    Page<CurriculumChannelDTO> curriculumChannelDTOPage =
        this.curriculumService.findAllInChannel(request.getChannelId(), pageable);
    List<ChannelCurriculumResponse> channelCurriculumResponses =
        curriculumChannelDTOPage.stream()
            .map(
                curriculumChannelDTO ->
                    ChannelCurriculumResponse.builder()
                        .id(curriculumChannelDTO.getId())
                        .title(curriculumChannelDTO.getTitle())
                        .headLine(curriculumChannelDTO.getHeadLine())
                        .cost(curriculumChannelDTO.getCost())
                        .description(curriculumChannelDTO.getDescription())
                        .requirement(curriculumChannelDTO.getRequirement())
                        .thumbnail(curriculumChannelDTO.getThumbnail())
                        .totalSessions(curriculumChannelDTO.getTotalSessions())
                        .totalTimesStringFormat(
                            DurationConverterUtil.toStringDuration(
                                Duration.ofSeconds(
                                    curriculumChannelDTO.getTotalDuration() != null
                                        ? curriculumChannelDTO.getTotalDuration()
                                        : 0)))
                        .build())
            .toList();

    return BaseResponse.build(
        PaginationResponse.<ChannelCurriculumResponse>builder()
            .data(channelCurriculumResponses)
            .currentPage(request.getCurrentPage())
            .totalPages(curriculumChannelDTOPage.getTotalPages())
            .totalElements(curriculumChannelDTOPage.getNumberOfElements())
            .build(),
        true);
  }

  @Override
  public Long findChannelIdByUserId(Long userId) {
    Channel channel = this.channelService.findChannelByUserId(userId).orElse(null);
    boolean isNotFoundChannel = channel == null;
    if (isNotFoundChannel) return -1L;
    return channel.getUserId();
  }
}
