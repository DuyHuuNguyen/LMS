package com.james.LMS.repository;

import com.james.LMS.entity.TrainingSession;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    @Query(value = """
     select count(*)
        from groups g
        left join training_sessions ts on ts.group_id = g.id
        where ts.is_active  and g.is_active
    """,nativeQuery = true)
    Integer countTrainingSessionByMonthAndId(Integer month, Long groupId);

    @EntityGraph(attributePaths = "curriculumTrainingSets")
    Optional<TrainingSession> findWithCurriculumTrainingSetsById(Long id);


    @EntityGraph(attributePaths = {"curriculumTrainingSets","trainingExams"})
    List<TrainingSession> findByGroup_IdAndStartedAtBetween(
            Long groupId,
            Long startOfMonth,
            Long endOfMonth);
}
