package com.james.LMS.util.chain_responsibility.client;

import com.james.LMS.util.chain_responsibility.impl.ContainExamInCurriculumHandler;
import com.james.LMS.util.chain_responsibility.impl.ContainSessionInCurriculumHandler;
import com.james.LMS.util.chain_responsibility.impl.OwnerCurriculumHandler;
import com.james.LMS.util.chain_responsibility.request.OwnerExamInCurriculumRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class OwnerExamClient {

  private final OwnerCurriculumHandler ownerCurriculumHandler;
  private final ContainSessionInCurriculumHandler containSessionInCurriculumHandler;
  private final ContainExamInCurriculumHandler containExamInCurriculumHandler;

  private void buildChainResponsibility() {
    this.ownerCurriculumHandler.addNext(this.containSessionInCurriculumHandler);
    this.containSessionInCurriculumHandler.addNext(containExamInCurriculumHandler);
  }

  public void validUserHasExamInCurriculum(
      OwnerExamInCurriculumRequest ownerExamInCurriculumRequest) {

    log.info("Location {}", System.identityHashCode(this));
    this.buildChainResponsibility();
    this.ownerCurriculumHandler.handleRequest(ownerExamInCurriculumRequest);
  }
}
