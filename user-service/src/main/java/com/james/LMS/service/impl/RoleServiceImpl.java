package com.james.LMS.service.impl;

import com.james.LMS.entity.Role;
import com.james.LMS.enums.RoleEnum;
import com.james.LMS.repository.RoleRepository;
import com.james.LMS.service.RoleService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Override
  public List<Role> findAllByUserId(Long userId) {
    return this.roleRepository.findAllByUserId(userId);
  }

  @Override
  public Optional<Role> findByRoleName(RoleEnum roleName) {
    return this.roleRepository.findRoleByRoleName(roleName);
  }
}
