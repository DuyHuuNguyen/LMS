package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.TestDTO;
import com.james.LMS.entity.Exam;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.facade.ExamFacade;
import com.james.LMS.request.ExamDetailRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.ExamDetailResponse;
import com.james.LMS.service.ExamService;
import com.james.LMS.util.chain_responsibility.client.OwnerExamClient;
import com.james.LMS.util.chain_responsibility.request.OwnerExamInCurriculumRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamFacadeImpl implements ExamFacade {
  private final ExamService examService;
  private final OwnerExamClient ownerExamClient;

  @Override
  public BaseResponse<ExamDetailResponse> findExamDetail(ExamDetailRequest examDetailRequest) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    OwnerExamInCurriculumRequest ownerExamInCurriculumRequest =
        OwnerExamInCurriculumRequest.builder()
            .userId(principal.getId())
            .curriculumId(examDetailRequest.getCurriculumId())
            .sessionId(examDetailRequest.getSessionId())
            .examId(examDetailRequest.getExamId())
            .build();

    try {
      this.ownerExamClient.validUserHasExamInCurriculum(ownerExamInCurriculumRequest);
    } catch (Exception e) {
      throw new EntityNotFoundException(ErrorCode.EXAM_NOT_FOUND);
    }
    Exam exam =
        this.examService
            .findExamFetchTestAndSessionById(1L)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.EXAM_NOT_FOUND));
    List<TestDTO> testDTOS =
        exam.getTests().stream()
            .map(
                test ->
                    TestDTO.builder()
                        .id(test.getId())
                        .index(test.getIndex())
                        .question(test.getQuestion())
                        .chooses(test.getChooses())
                        .answer(test.getAnswer())
                        .build())
            .sorted()
            .toList();

    return BaseResponse.build(
        ExamDetailResponse.builder()
            .id(exam.getId())
            .name(exam.getName())
            .index(exam.getIndex())
            .isPreview(exam.getIsPreview())
            .testDTOS(testDTOS)
            .build(),
        true);
  }
}
