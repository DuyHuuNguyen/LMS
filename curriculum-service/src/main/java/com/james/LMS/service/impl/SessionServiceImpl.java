package com.james.LMS.service.impl;

import com.james.LMS.entity.Session;
import com.james.LMS.repository.SessionRepository;
import com.james.LMS.service.SessionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
  private final SessionRepository sessionRepository;

  @Override
  public List<Session> findAllByCurriculumId(Long curriculumId) {
    return this.sessionRepository.findAllByCurriculumId(curriculumId);
  }
}
