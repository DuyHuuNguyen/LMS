package com.james.LMS.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ChannelService {
  Long findChannelIdByUserId(Long userId);
}
