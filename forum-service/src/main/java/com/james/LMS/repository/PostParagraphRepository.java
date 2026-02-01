package com.james.LMS.repository;

import com.james.LMS.entity.PostParagraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostParagraphRepository extends JpaRepository<PostParagraph, Long> {}
