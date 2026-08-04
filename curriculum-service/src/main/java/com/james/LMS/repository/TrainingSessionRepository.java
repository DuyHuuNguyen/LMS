package com.james.LMS.repository;

import com.james.LMS.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {}
