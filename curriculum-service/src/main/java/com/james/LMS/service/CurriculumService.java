package com.james.LMS.service;

import com.james.LMS.entity.Curriculum;
import java.util.Optional;

public interface CurriculumService {
  Optional<Curriculum> findById(Long id);
}
