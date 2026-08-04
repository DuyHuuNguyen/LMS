package com.james.LMS.service;

import com.james.LMS.dto.CompanyGroupDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface GroupService {
  Slice<CompanyGroupDTO> findAllByCompanyIdAndUserAdminCompanyId(
      Long companyId, Long userAdminCompanyId, Pageable pageable);
}
