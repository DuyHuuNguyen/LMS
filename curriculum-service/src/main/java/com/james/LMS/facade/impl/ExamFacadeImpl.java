package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.CreateTestDTO;
import com.james.LMS.dto.TestDTO;
import com.james.LMS.entity.Exam;
import com.james.LMS.entity.Session;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.enums.MessageType;
import com.james.LMS.enums.SourceMessageEnum;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.facade.ExamFacade;
import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.CreateTestsPayload;
import com.james.LMS.request.AddNewExamRequest;
import com.james.LMS.request.ExamDetailRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.ExamDetailResponse;
import com.james.LMS.service.ExamService;
import com.james.LMS.service.ProducerCreateNewExamService;
import com.james.LMS.service.SessionService;
import com.james.LMS.util.chain_responsibility.client.InstructorCreateCurriculumContentClient;
import com.james.LMS.util.chain_responsibility.client.OwnerExamClient;
import com.james.LMS.util.chain_responsibility.request.InstructorCreateCurriculumContentRequest;
import com.james.LMS.util.chain_responsibility.request.OwnerExamInCurriculumRequest;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamFacadeImpl implements ExamFacade {
  private final ExamService examService;
  private final OwnerExamClient ownerExamClient;
  private final InstructorCreateCurriculumContentClient instructorCreateCurriculumContentClient;
  private final ProducerCreateNewExamService producerCreateNewExamService;
  private final SessionService sessionService;

  @Override
  public BaseResponse<ExamDetailResponse> findExamDetail(ExamDetailRequest examDetailRequest) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    OwnerExamInCurriculumRequest ownerExamInCurriculumRequest =
        OwnerExamInCurriculumRequest.builder()
            .userId(principal.getId()).isInstructor(false)
            .curriculumId(examDetailRequest.getCurriculumId())
            .sessionId(examDetailRequest.getSessionId())
            .examId(examDetailRequest.getExamId())
            .build();

    try {
      this.ownerExamClient.validUserHasExamInCurriculum(ownerExamInCurriculumRequest);
    } catch (Exception e) {
      log.error(e.getMessage());
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

  @Override
  @Transactional
  public BaseResponse<Void> addNewExam(AddNewExamRequest addNewExamRequest) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    InstructorCreateCurriculumContentRequest instructorCreateCurriculumContentRequest =
        InstructorCreateCurriculumContentRequest.builder()
            .userId(principal.getId())
            .curriculumId(addNewExamRequest.getCurriculumId())
            .build();
    try {
      this.instructorCreateCurriculumContentClient.validInstructorCreateCurriculumContent(
          instructorCreateCurriculumContentRequest);
    } catch (Exception e) {
      throw new PermissionDeniedException(ErrorCode.CURRICULUM_NOT_FOUND);
    }

    Session session =
        this.sessionService
            .findByIdAndCurriculumId(
                addNewExamRequest.getSessionId(), addNewExamRequest.getCurriculumId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SESSION_NOT_FOUND));

    Exam exam =
        Exam.builder()
            .session(session)
            .index(addNewExamRequest.getIndex())
            .name(addNewExamRequest.getName())
            .isPreview(addNewExamRequest.getIsPreview())
            .build();
    Exam examStored = this.examService.saveAndFetch(exam);

    List<CreateTestDTO> createTestDTOS =
        addNewExamRequest.getTestDTOS() == null
            ? Collections.emptyList()
            : addNewExamRequest.getTestDTOS().stream()
                .map(
                    testDTO ->
                        new CreateTestDTO(
                            testDTO.getIndex(),
                            testDTO.getQuestion(),
                            testDTO.getChooses(),
                            testDTO.getAnswer()))
                .toList();

    BaseMessage<CreateTestsPayload> createTestsBaseMessage =
        BaseMessage.<CreateTestsPayload>builder()
            .type(MessageType.CREATE_TESTS_FOR_EXAM)
            .source(SourceMessageEnum.CURRICULUM_SERVICE)
            .createdAt(Instant.now())
            .payload(new CreateTestsPayload(examStored.getId(), createTestDTOS))
            .build();
    this.producerCreateNewExamService.send(createTestsBaseMessage);

    log.info(examStored.toString());

    return BaseResponse.ok();
  }
}
