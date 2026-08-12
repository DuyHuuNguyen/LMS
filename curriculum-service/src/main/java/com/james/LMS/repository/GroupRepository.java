package com.james.LMS.repository;

import com.james.LMS.dto.CompanyGroupDTO;
import com.james.LMS.entity.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    Slice<CompanyGroupDTO> findAllByCompanyIdAndUserAdminCompanyId(@Param("companyId") Long companyId, @Param("userAdminCompanyId") Long userAdminCompanyId, Pageable pageable);

    Integer countById(Long id);

    Boolean existsByIdAndUserAdminGroupId(Long id, Long userAdminGroupId);

    @Query(value = """
      select  exists(
            select *
            from companies c
            join groups g on g.company_id =c.id
            where c.id =:companyId and g.id =:groupId and g.is_active and g.user_admin_group_id =:userId
    )
  """, nativeQuery = true)
    Boolean isUserAdminGroupInCompanyAccessibleToGroup(@Param("userId") Long userId, @Param("companyId") Long companyId, @Param("groupId") Long groupId);



    @Query(value = """
        select  exists(
              select *
              from companies c
              join groups g on g.company_id =c.id
              where c.id =:companyId and g.id =:groupId and g.is_active and ( c.user_admin_company_id =:userId or g.user_admin_group_id =:userId)
          )
  """, nativeQuery = true)
    Boolean isUserAccessibleToGroup(@Param("userId") Long userId, @Param("companyId") Long companyId, @Param("groupId") Long groupId);
}
