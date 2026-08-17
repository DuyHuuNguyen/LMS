package com.james.LMS.service;

import com.james.LMS.dto.*;

public interface CurriculumValidatorService {
  Boolean isPurchasedCurriculumToHaveExam(ValidateExamAccessDTO validateExamAccessDTO);

  Boolean isPurchasedCurriculumToHaveVideo(ValidateVideoAccessDTO validateVideoAccessDTO);

  Boolean isExistedChannelAndCurriculumForUploadVideo(
      ValidVideoUploadingAccessDTO validVideoUploadingAccessDTO);

  Boolean isExistedChannelAndCurriculumForChangeSession(
      ValidChangeSessionVideoAccessDTO validChangeSessionVideoAccessDTO);

  Boolean isInstructorHoldVideo(ValidInstructorHoldVideoDTO validInstructorHoldVideoDTO);

  Boolean isPurchasedCurriculum(
      ValidUserPurchasedCurriculumAccessDTO validUserPurchasedCurriculumAccessDTO);

  Boolean isPurchasedCurriculumWithVideoInSession(
      ValidatePurchasedCurriculumAndContainSessionAndSessionContentDTO dto);

  Boolean isPurchasedCurriculumWithExamInSession(
      ValidatePurchasedCurriculumAndContainSessionAndSessionContentDTO dto);
}
