package com.james.LMS.service.impl;

import com.james.LMS.dto.ExamDTO;
import com.james.LMS.entity.Exam;
import com.james.LMS.repository.ExamRepository;
import com.james.LMS.service.ExamService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
  private final ExamRepository examRepository;

  @Override
  public List<Exam> findAllBySessionIds(List<Long> sessionIds) {
    return this.examRepository.findAllBySessionIds(sessionIds);
  }

  @Override
  public List<ExamDTO> findExamDTOBySessionIds(List<Long> sessionIds) {
    return this.examRepository.findExamDTOBySessionIds(sessionIds);
  }

  @Override
  public Boolean existByExamIdAndSessionId(Long examId, Long sessionId) {
    return this.examRepository.existsByIdAndSession_IdAndIsActiveIsTrue(examId, sessionId);
  }

  @Override
  public Optional<Exam> findExamFetchTestAndSessionById(Long id) {
    return this.examRepository.findExamFetchTestsAndSessionById(id);
  }
}
