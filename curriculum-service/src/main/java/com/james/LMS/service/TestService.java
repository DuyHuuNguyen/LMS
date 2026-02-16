package com.james.LMS.service;

import com.james.LMS.entity.Test;
import java.util.List;

public interface TestService {
  List<Test> saveAll(List<Test> tests);
}
