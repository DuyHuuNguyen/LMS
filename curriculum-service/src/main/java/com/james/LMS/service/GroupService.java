package com.james.LMS.service;

import com.james.LMS.dto.CompanyGroupDTO;
import com.james.LMS.entity.Group;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface GroupService {
  Slice<CompanyGroupDTO> findAllByCompanyIdAndUserAdminCompanyId(
      Long companyId, Long userAdminCompanyId, Pageable pageable);

  CompletableFuture<Integer> countMembersByGroupId(Long id);

  Boolean isGroupAdmin(Long userId, Long groupId);

  Boolean isUserAdminGroupInCompanyAccessibleToGroup(Long userId, Long companyId, Long groupId);

  Boolean isUserAccessibleToGroup(Long userId, Long companyId, Long groupId);

  Optional<Group> findById(Long id);
}
