package com.james.LMS.util.chain_responsibility.client;

import com.james.LMS.util.chain_responsibility.impl.OwnerCurriculumHandler;
import com.james.LMS.util.chain_responsibility.request.InstructorCreateCurriculumContentRequest;
import com.james.LMS.util.chain_responsibility.request.OwnerExamInCurriculumRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class InstructorCreateCurriculumContentClient {

  private final OwnerCurriculumHandler ownerCurriculumHandler;

  public void validInstructorCreateCurriculumContent(
      InstructorCreateCurriculumContentRequest instructorCreateCurriculumContentRequest) {
    OwnerExamInCurriculumRequest ownerExamInCurriculumRequest =
        OwnerExamInCurriculumRequest.builder()
            .userId(instructorCreateCurriculumContentRequest.getUserId())
            .isInstructor(true)
            .curriculumId(instructorCreateCurriculumContentRequest.getCurriculumId())
            .build();
    this.ownerCurriculumHandler.handleRequest(ownerExamInCurriculumRequest);
  }
}
