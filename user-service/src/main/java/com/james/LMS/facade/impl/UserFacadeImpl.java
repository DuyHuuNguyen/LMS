package com.james.LMS.facade.impl;

import com.james.LMS.facade.UserFacade;
import com.james.LMS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
  private final UserService userService;
}
