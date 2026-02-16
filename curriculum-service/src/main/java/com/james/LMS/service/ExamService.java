package com.james.LMS.service;

import com.james.LMS.dto.ExamDTO;
import com.james.LMS.entity.Exam;
import java.util.List;
import java.util.Optional;

public interface ExamService {
  List<Exam> findAllBySessionIds(List<Long> sessionIds);

  List<ExamDTO> findExamDTOBySessionIds(List<Long> sessionIds);

  Boolean existByExamIdAndSessionId(Long examId, Long sessionId);

  Optional<Exam> findExamFetchTestAndSessionById(Long id);

  Exam saveAndFetch(Exam exam);

  Optional<Exam> findByIdAndIsActiveIsTrue(Long Id);
}
