package com.james.LMS.service;

import com.james.LMS.entity.Session;
import java.util.List;

public interface SessionService {
  List<Session> findAllByCurriculumId(Long curriculumId);
}
