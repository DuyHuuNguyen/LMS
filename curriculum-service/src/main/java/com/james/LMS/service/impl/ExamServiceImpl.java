package com.james.LMS.service.impl;

import com.james.LMS.repository.ExamRepository;
import com.james.LMS.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
  private final ExamRepository examRepository;
}
