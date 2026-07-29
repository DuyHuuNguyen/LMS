package com.james.LMS.repository;

import com.james.LMS.entity.MultipleChoiceTestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultipleChoiceTestAnswerRepository
    extends JpaRepository<MultipleChoiceTestAnswer, Long> {}
