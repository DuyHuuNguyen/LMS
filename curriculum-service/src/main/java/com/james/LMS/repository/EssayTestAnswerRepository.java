package com.james.LMS.repository;

import com.james.LMS.entity.EssayTestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssayTestAnswerRepository extends JpaRepository<EssayTestAnswer, Long> {}
