package com.james.LMS.service;

import com.james.LMS.dto.ValidChangeSessionVideoAccessDTO;
import com.james.LMS.dto.ValidVideoUploadingAccessDTO;
import com.james.LMS.dto.ValidateExamAccessDTO;
import com.james.LMS.dto.ValidateVideoAccessDTO;

public interface CurriculumValidatorService {
  Boolean isPurchasedCurriculumToHaveExam(ValidateExamAccessDTO validateExamAccessDTO);

  Boolean isPurchasedCurriculumToHaveVideo(ValidateVideoAccessDTO validateVideoAccessDTO);

  Boolean isExistedChannelAndCurriculumForUploadVideo(
      ValidVideoUploadingAccessDTO validVideoUploadingAccessDTO);

  Boolean isExistedChannelAndCurriculumForChangeSession(
      ValidChangeSessionVideoAccessDTO validChangeSessionVideoAccessDTO);

  Boolean isInstructorHoldVideo(ValidInstructorHoldVideoDTO validInstructorHoldVideoDTO);
}
