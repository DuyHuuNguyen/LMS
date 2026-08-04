package com.james.LMS.service;

import com.james.LMS.entity.Company;
import java.util.Optional;

public interface CompanyService {
  Optional<Company> findById(Long id);

  Optional<Company> findByUserAdminId(Long userAdminId);

  void save(Company company);
}
