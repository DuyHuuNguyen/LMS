package com.james.LMS.repository;

import com.james.LMS.entity.CompanyPossessCurriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyPossessCurriculumRepository
    extends JpaRepository<CompanyPossessCurriculum, Long> {}
