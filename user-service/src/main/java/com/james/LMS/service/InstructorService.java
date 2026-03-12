package com.james.LMS.service;

import com.james.LMS.entity.Instructor;
import java.util.Optional;

public interface InstructorService {
  Optional<Instructor> findById(Long id);
}
