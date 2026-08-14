package com.james.LMS.repository;

import com.james.LMS.dto.CurriculumAuditLearningProgressDTO;
import com.james.LMS.entity.LearningProgress;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearningProgressRepository extends JpaRepository<LearningProgress,Long> {


    @Query("""
        select lp
        from LearningProgress lp
        where lp.userCurriculum.curriculum.id =:curriculumId and lp.userCurriculum.userId =:userId and lp.isActive
    """)
    Optional<LearningProgress> findByUserIdAndCurriculumId(@Param("userId") Long userId, @Param("curriculumId") Long curriculumId);

    @Query("""
        select new com.james.LMS.dto.CurriculumAuditLearningProgressDTO(
            lp.userCurriculum.curriculum.id,
                lp.learningMinutes,
                lp.userCurriculum.curriculum.title,
            lp.userCurriculum.curriculum.curriculumAudit.totalDurationSeconds,
            lp.userCurriculum.curriculum.thumbnail)
        from LearningProgress  lp 
        where lp.isActive and lp.userCurriculum.userId =:userId
    """)
    Slice<CurriculumAuditLearningProgressDTO> findAllByUserId(@Param("userId") Long userId ,Pageable pageable);

    Slice<LearningProgress> findAllByUserCurriculumUserIdAndIsActiveTrue(Long userId, Pageable pageable);
}
