package com.james.LMS.service;

import com.james.LMS.dto.InstructorDTO;
import java.util.Optional;

public interface InstructorService {
  Optional<InstructorDTO> findByUserId(Long userId);
}
