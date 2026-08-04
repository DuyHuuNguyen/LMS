package com.james.LMS.service.impl;

import com.james.LMS.entity.Company;
import com.james.LMS.repository.CompanyRepository;
import com.james.LMS.service.CompanyService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
  private final CompanyRepository companyRepository;

  @Override
  public Optional<Company> findById(Long id) {
    return companyRepository.findById(id);
  }

  @Override
  public Optional<Company> findByUserAdminId(Long userAdminId) {
    return this.companyRepository.findByUserAdminCompanyId(userAdminId);
  }

  @Override
  public void save(Company company) {
    this.companyRepository.save(company);
  }
}
