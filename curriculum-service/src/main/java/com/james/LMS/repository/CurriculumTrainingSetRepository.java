package com.james.LMS.repository;

import com.james.LMS.entity.CurriculumTrainingSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumTrainingSetRepository
    extends JpaRepository<CurriculumTrainingSet, Long> {}
