package com.james.LMS.service.impl;

import com.james.LMS.entity.Test;
import com.james.LMS.repository.TestRepository;
import com.james.LMS.service.TestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
  private final TestRepository testRepository;

  @Override
  public List<Test> saveAll(List<Test> tests) {
    return this.testRepository.saveAll(tests);
  }
}
