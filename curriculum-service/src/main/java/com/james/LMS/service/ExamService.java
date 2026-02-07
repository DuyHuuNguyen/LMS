package com.james.LMS.service;

import com.james.LMS.dto.ExamDTO;
import com.james.LMS.entity.Exam;
import java.util.List;

public interface ExamService {
  List<Exam> findAllBySessionIds(List<Long> sessionIds);

  List<ExamDTO> findExamDTOBySessionIds(List<Long> sessionIds);
}
