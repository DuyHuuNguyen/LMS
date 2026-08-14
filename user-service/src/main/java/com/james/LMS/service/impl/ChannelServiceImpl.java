package com.james.LMS.service.impl;

import com.james.LMS.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
  private final RestTemplate restTemplate;

  @Value("${curriculum-service}")
  private String curriculumServiceName;

  @Override
  public Long findChannelIdByUserId(Long userId) {
    String url =
        String.format(
            "https://%s/api/v1/channels/internal/channel-id?userId=%s",
            curriculumServiceName, userId);

    return restTemplate.getForObject(url, Long.class, userId);
  }
}
