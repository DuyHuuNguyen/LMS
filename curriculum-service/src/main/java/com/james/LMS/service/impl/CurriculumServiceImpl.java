package com.james.LMS.service.impl;

import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.service.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumServiceImpl implements CurriculumService {
  private final CurriculumRepository curriculumRepository;
}
