package com.james.LMS.service;

import com.james.LMS.entity.Channel;
import java.util.Optional;

public interface ChannelService {
  Optional<Channel> findByCurriculumId(Long curriculumId);
}
