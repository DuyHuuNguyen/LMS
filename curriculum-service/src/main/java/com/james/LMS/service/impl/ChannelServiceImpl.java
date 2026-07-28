package com.james.LMS.service.impl;

import com.james.LMS.entity.Channel;
import com.james.LMS.repository.ChannelRepository;
import com.james.LMS.service.ChannelService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
  private final ChannelRepository channelRepository;

  @Override
  public Optional<Channel> findByCurriculumId(Long curriculumId) {
    return Optional.empty();
  }

  @Override
  public Optional<Channel> findChannelByUserId(Long userId) {
    return this.channelRepository.findChannelByUserId(userId);
  }

  @Override
  public Boolean verifyChannelOfLecturer(Long userId, Long channelId) {
    return this.channelRepository.verifyChannelOfLecturer(userId,channelId);
  }
}
