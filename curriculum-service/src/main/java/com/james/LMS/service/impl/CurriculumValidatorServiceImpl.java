package com.james.LMS.service.impl;

import com.james.LMS.dto.ValidateExamAccessDTO;
import com.james.LMS.dto.ValidateVideoAccessDTO;
import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.service.CurriculumValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumValidatorServiceImpl implements CurriculumValidatorService {
  private final CurriculumRepository curriculumRepository;

  @Override
  public Boolean isPurchasedCurriculumToHaveExam(ValidateExamAccessDTO validateExamAccessDTO) {
    return this.curriculumRepository.isPurchasedCurriculumToHaveExam(
        validateExamAccessDTO.getUserId(),
        validateExamAccessDTO.getCurriculumId(),
        validateExamAccessDTO.getSessionId(),
        validateExamAccessDTO.getExamId());
  }

  @Override
  public Boolean isPurchasedCurriculumToHaveVideo(ValidateVideoAccessDTO validateVideoAccessDTO) {
    return this.curriculumRepository.isPurchasedCurriculumToHaveVideo(
        validateVideoAccessDTO.getUserId(),
        validateVideoAccessDTO.getCurriculumId(),
        validateVideoAccessDTO.getSessionId(),
        validateVideoAccessDTO.getVideoId());
  }
}
