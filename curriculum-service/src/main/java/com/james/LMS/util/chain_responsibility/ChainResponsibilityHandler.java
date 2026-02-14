package com.james.LMS.util.chain_responsibility;

public interface ChainResponsibilityHandler {
  void addNext(ChainResponsibilityHandler chainResponsibilityHandler);

  void handleRequest(Object object);
}
