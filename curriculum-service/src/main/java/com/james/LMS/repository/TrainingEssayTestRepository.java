package com.james.LMS.repository;

import com.james.LMS.entity.TrainingEssayTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingEssayTestRepository extends JpaRepository<TrainingEssayTest, Long> {}
