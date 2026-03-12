package com.james.LMS.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CURRICULUM-SERVICE")
public interface ChannelService {
  @GetMapping(value = "/api/v1/channels/internal/id")
  Long findChannelIdByUserId(@RequestParam Long userId);
}
