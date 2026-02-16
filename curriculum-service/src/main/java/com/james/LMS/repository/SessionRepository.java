package com.james.LMS.repository;

import com.james.LMS.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(value = """
    select s
    from Session s
    where s.curriculum.id = :curriculumId
    ORDER BY s.index
    """)
    List<Session> findAllByCurriculumId(Long curriculumId);


    Boolean existsByIdAndCurriculum_IdAndIsActiveIsTrue(Long userId,Long curriculumId);

    Optional<Session> findByIdAndCurriculum_Id(Long id, Long curriculumId);
}
