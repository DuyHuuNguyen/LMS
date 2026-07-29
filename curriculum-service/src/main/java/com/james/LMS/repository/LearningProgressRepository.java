package com.james.LMS.repository;

import com.james.LMS.entity.LearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearningProgressRepository extends JpaRepository<LearningProgress,Long> {


    @Query("""
        select lp
        from LearningProgress lp
        where lp.userCurriculum.curriculum.id =:curriculumId and lp.userCurriculum.userId =:userId and lp.isActive
    """)
    Optional<LearningProgress> findByUserIdAndCurriculumId(Long userId, Long curriculumId);
}
