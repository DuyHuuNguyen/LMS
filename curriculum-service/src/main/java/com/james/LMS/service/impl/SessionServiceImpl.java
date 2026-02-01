package com.james.LMS.service.impl;

import com.james.LMS.repository.SessionRepository;
import com.james.LMS.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
  private final SessionRepository sessionRepository;
}
