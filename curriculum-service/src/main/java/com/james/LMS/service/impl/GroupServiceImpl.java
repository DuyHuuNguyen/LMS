package com.james.LMS.service.impl;

import com.james.LMS.dto.CompanyGroupDTO;
import com.james.LMS.repository.GroupRepository;
import com.james.LMS.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;

  @Override
  public Slice<CompanyGroupDTO> findAllByCompanyIdAndUserAdminCompanyId(
      Long companyId, Long userAdminCompanyId, Pageable pageable) {
    return this.groupRepository.findAllByCompanyIdAndUserAdminCompanyId(
        companyId, userAdminCompanyId, pageable);
  }
}
