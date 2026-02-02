package com.james.LMS.service;

import com.james.LMS.entity.Role;
import com.james.LMS.enums.RoleEnum;
import java.util.List;
import java.util.Optional;

public interface RoleService {
  List<Role> findAllByUserId(Long userId);

  Optional<Role> findByRoleName(RoleEnum roleName);
}
