package com.james.LMS.repository;

import com.james.LMS.entity.Role;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query(
            value = """
        SELECT r.*
        FROM user_roles ur
        JOIN roles r
            ON ur.role_id = r.id
        WHERE ur.user_id = :userId
          AND ur.is_active = true
    """,
            nativeQuery = true
    )
    List<Role> findAllByUserId(@Param("userId") Long userId);

}
