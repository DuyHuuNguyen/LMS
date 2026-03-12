package com.james.LMS.service.impl;

import com.james.LMS.entity.Instructor;
import com.james.LMS.repository.InstructorRepository;
import com.james.LMS.service.InstructorService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
  private final InstructorRepository instructorRepository;

  @Override
  public Optional<Instructor> findById(Long id) {
    return this.instructorRepository.findById(id);
  }
}
