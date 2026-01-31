package com.james.LMS.service;

import com.james.LMS.entity.Role;
import java.util.List;

public interface RoleService {
  List<Role> findAllByUserId(Long userId);
}
