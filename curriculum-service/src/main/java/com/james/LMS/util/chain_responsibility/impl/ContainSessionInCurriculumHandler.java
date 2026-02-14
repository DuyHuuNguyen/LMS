package com.james.LMS.util.chain_responsibility.impl;

import com.james.LMS.service.SessionService;
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
public class ContainSessionInCurriculumHandler implements ChainResponsibilityHandler {
  private ChainResponsibilityHandler next;

  private final SessionService sessionService;

  @Override
  public void addNext(ChainResponsibilityHandler chainResponsibilityHandler) {
    this.next = chainResponsibilityHandler;
  }

  @Override
  public void handleRequest(Object object) {

    log.info("Location {}", System.identityHashCode(this));
    OwnerExamInCurriculumRequest curriculumRequest = (OwnerExamInCurriculumRequest) object;

    boolean isHasSessionInCurriculum =
        this.sessionService.existsByIdAndCurriculumId(
            curriculumRequest.getSessionId(), curriculumRequest.getCurriculumId());
    if (!isHasSessionInCurriculum) throw new RuntimeException("Session not found in curriculum");
    log.info("Pass");

    boolean isEndOfChainResponsibility = this.next == null;
    if (!isEndOfChainResponsibility) this.next.handleRequest(curriculumRequest);
  }
}
