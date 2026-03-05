package com.james.LMS.service.impl;

import com.james.LMS.dto.ValidChangeSessionVideoAccessDTO;
import com.james.LMS.dto.ValidVideoUploadingAccessDTO;
import com.james.LMS.dto.ValidateExamAccessDTO;
import com.james.LMS.dto.ValidateVideoAccessDTO;
import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.service.CurriculumValidatorService;
import com.james.LMS.service.ValidInstructorHoldVideoDTO;
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

  @Override
  public Boolean isExistedChannelAndCurriculumForUploadVideo(
      ValidVideoUploadingAccessDTO validVideoUploadingAccessDTO) {
    return this.curriculumRepository.isExistedChannelAndCurriculumForUploadVideo(
        validVideoUploadingAccessDTO.getUserChanelHolderId(),
        validVideoUploadingAccessDTO.getCurriculumId(),
        validVideoUploadingAccessDTO.getSessionId());
  }

  @Override
  public Boolean isExistedChannelAndCurriculumForChangeSession(
      ValidChangeSessionVideoAccessDTO validChangeSessionVideoAccessDTO) {
    return this.curriculumRepository.isExistedChannelAndCurriculumForUploadVideo(
        validChangeSessionVideoAccessDTO.getUserChanelHolderId(),
        validChangeSessionVideoAccessDTO.getCurriculumId(),
        validChangeSessionVideoAccessDTO.getNewSessionId());
  }

  @Override
  public Boolean isInstructorHoldVideo(ValidInstructorHoldVideoDTO validInstructorHoldVideoDTO) {
    return this.curriculumRepository.isInstructorHoldVideo(
        validInstructorHoldVideoDTO.getUserId(),
        validInstructorHoldVideoDTO.getCurriculumId(),
        validInstructorHoldVideoDTO.getVideoId());
  }
}
