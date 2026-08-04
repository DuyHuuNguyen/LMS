package com.james.LMS.repository;

import com.james.LMS.dto.CompanyGroupDTO;
import com.james.LMS.entity.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query(value = """
    select g.id  as groupId
     ,g.group_name as groupName
     , count(gm.id) as totalMembers
    , g.max_group_size as maxGroupSize
    from "groups" g
    join companies c on c.id = g.company_id
    left join group_members gm on g.id =gm.group_id
    where g.is_active and c.id =:companyId and c.user_admin_company_id =:userAdminCompanyId
    group by g.id,g.company_id ,g.group_name,c.company_name
    """,nativeQuery = true)
    Slice<CompanyGroupDTO> findAllByCompanyIdAndUserAdminCompanyId(Long companyId,Long userAdminCompanyId, Pageable pageable);
}
