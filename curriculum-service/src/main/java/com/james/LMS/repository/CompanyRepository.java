package com.james.LMS.repository;

import com.james.LMS.entity.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

  Optional<Company> findByUserAdminCompanyId(Long userAdminCompanyId);

  Boolean existsByIdAndUserAdminCompanyId(Long id, Long userAdminCompanyId);

  @Query(value = """
  select  exists(
        select *
        from companies c
        join groups g on g.company_id =c.id
        where c.id =:companyId and g.id =:groupId and g.is_active and c.user_admin_company_id =:userId
    )
  """, nativeQuery = true)
  Boolean isUserAdminCompanyAccessibleToGroup(@Param("userId") Long userId, @Param("companyId") Long companyId, @Param("groupId") Long groupId);

}
