package com.james.LMS.repository;

import com.james.LMS.entity.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

  Optional<Company> findByUserAdminCompanyId(Long userAdminCompanyId);
}
