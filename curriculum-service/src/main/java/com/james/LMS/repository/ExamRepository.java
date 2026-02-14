package com.james.LMS.repository;

import com.james.LMS.dto.ExamDTO;
import com.james.LMS.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query(value = """
    select e
    from Exam  e
    where e.session.id in (:sessionIds)
    """)
    List<Exam> findAllBySessionIds(List<Long> sessionIds);

    @Query(value = """
    select new com.james.LMS.dto.ExamDTO(
         e.id,
           e.name,
           e.isPreview,
           e.index,
           e.session.id
        )
    from Exam  e
    where e.session.id in (:sessionIds)
    """)
    List<ExamDTO> findExamDTOBySessionIds(List<Long> sessionIds);

    Boolean existsByIdAndSession_IdAndIsActiveIsTrue(Long examId,Long sessionId);


    @Query(value = """
    select distinct e
    from Exam e
    join fetch e.session s
    left join fetch e.tests t
    where e.id =:id and e.isActive = true
    """)
    Optional<Exam> findExamFetchTestsAndSessionById(Long id);
}
