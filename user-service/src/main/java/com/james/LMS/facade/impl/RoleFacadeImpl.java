package com.james.LMS.facade.impl;

import com.james.LMS.facade.RoleFacade;
import com.james.LMS.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleFacadeImpl implements RoleFacade {
  private final RoleService roleService;
}
