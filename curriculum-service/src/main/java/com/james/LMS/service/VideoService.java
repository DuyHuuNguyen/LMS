package com.james.LMS.service;

import com.james.LMS.dto.VideoDTO;
import com.james.LMS.entity.Video;
import java.util.List;

public interface VideoService {
  List<Video> findAllBySessionIds(List<Long> sessionIds);

  List<VideoDTO> findVideoDTOBySessionIds(List<Long> sessionIds);
}
