package com.james.LMS.service;

import com.james.LMS.dto.ValidateExamAccessDTO;
import com.james.LMS.dto.ValidateVideoAccessDTO;

public interface CurriculumValidatorService {
  Boolean isPurchasedCurriculumToHaveExam(ValidateExamAccessDTO validateExamAccessDTO);

  Boolean isPurchasedCurriculumToHaveVideo(ValidateVideoAccessDTO validateVideoAccessDTO);
}
