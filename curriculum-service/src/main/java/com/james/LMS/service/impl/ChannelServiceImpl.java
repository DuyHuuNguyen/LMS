package com.james.LMS.service.impl;

import com.james.LMS.repository.ChannelRepository;
import com.james.LMS.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
  private final ChannelRepository channelRepository;
}
