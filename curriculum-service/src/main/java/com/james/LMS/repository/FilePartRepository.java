package com.james.LMS.repository;

import com.james.LMS.entity.FilePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilePartRepository extends JpaRepository<FilePart, Long> {}
