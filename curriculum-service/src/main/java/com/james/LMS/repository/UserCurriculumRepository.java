package com.james.LMS.repository;

import com.james.LMS.entity.UserCurriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCurriculumRepository extends JpaRepository<UserCurriculum, Long> {}
