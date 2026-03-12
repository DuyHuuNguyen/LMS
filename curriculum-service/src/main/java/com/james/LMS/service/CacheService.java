package com.james.LMS.service;

import com.james.LMS.dto.InstructorDTO;
import java.util.concurrent.TimeUnit;

public interface CacheService {
  void store(String key, Object value, Integer timeOut, TimeUnit timeUnit);

  void store(String key, Object value);

  Object retrieve(String key);

  void delete(String key);

  Boolean hasKey(String key);

  InstructorDTO retrieveInstructorDTOAndRenewTTL(String key);
}
