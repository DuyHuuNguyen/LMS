package com.james.LMS.util.chain_responsibility.impl;

import com.james.LMS.service.CurriculumService;
import com.james.LMS.service.UserCurriculumService;
import com.james.LMS.util.chain_responsibility.ChainResponsibilityHandler;
import com.james.LMS.util.chain_responsibility.request.OwnerExamInCurriculumRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Scope("prototype")
@Component
@RequiredArgsConstructor
public class OwnerCurriculumHandler implements ChainResponsibilityHandler {
  private ChainResponsibilityHandler next;

  private final CurriculumService curriculumService;
  private final UserCurriculumService userCurriculumService;

  @Override
  public void addNext(ChainResponsibilityHandler chainResponsibilityHandler) {
    this.next = chainResponsibilityHandler;
  }

  @Override
  public void handleRequest(Object object) {

    log.info("Location {}", System.identityHashCode(this));
    OwnerExamInCurriculumRequest curriculumRequest = (OwnerExamInCurriculumRequest) object;

    boolean isExistsCurriculum =
        this.curriculumService.existsCurriculumById(curriculumRequest.getCurriculumId());
    if (!isExistsCurriculum) throw new RuntimeException("Curriculum not found");

    boolean isUserEnrolledInCurriculum = false;

    if (!curriculumRequest.getIsInstructor())
      isUserEnrolledInCurriculum =
          this.userCurriculumService.existsByUserIdAndCurriculumId(
              curriculumRequest.getUserId(), curriculumRequest.getCurriculumId());

    boolean isInstructorOfCurriculum =
        this.curriculumService.existsByIdAndChannelUserIdAndIsActiveIsTrue(
            curriculumRequest.getCurriculumId(), curriculumRequest.getUserId());
    log.info("is user {} | is instructor {}", isUserEnrolledInCurriculum, isInstructorOfCurriculum);

    if (isInstructorOfCurriculum || isUserEnrolledInCurriculum) {
      boolean isEndOfChainResponsibility = this.next == null;
      if (!isEndOfChainResponsibility) this.next.handleRequest(curriculumRequest);
    } else throw new RuntimeException("User do not enroll in curriculums");

    log.info("Pass");
  }
}
