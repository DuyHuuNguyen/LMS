package com.james.LMS.service.impl;

import com.james.LMS.repository.TestRepository;
import com.james.LMS.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
  private final TestRepository testRepository;
}
