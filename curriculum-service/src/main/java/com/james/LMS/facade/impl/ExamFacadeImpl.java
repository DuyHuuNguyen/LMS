package com.james.LMS.facade.impl;

import com.james.LMS.facade.ExamFacade;
import com.james.LMS.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamFacadeImpl implements ExamFacade {
  private final TestService testService;
}
