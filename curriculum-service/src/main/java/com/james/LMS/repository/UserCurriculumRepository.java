package com.james.LMS.repository;

import com.james.LMS.dto.UserCurriculumValidationDTO;
import com.james.LMS.entity.UserCurriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCurriculumRepository extends JpaRepository<UserCurriculum, Long> {

  Boolean existsUserCurriculumByUserIdAndCurriculum_IdAndIsActiveIsTrue(
      Long userId, Long curriculumId);



  @Query(value = """

      select uc.*
      from user_curriculums uc
      join curriculums c on uc.curriculum_id = c.id  and uc.is_active
      join sessions s  on uc.curriculum_id = c.id and uc.is_active
      join videos v on v.session_id = s.id and v.is_active
      where v.id = :videoId
           and uc.user_id  =:userId
           and uc.curriculum_id =:curriculumId
   
   """,nativeQuery = true)
  Optional<UserCurriculum> findByUserIdAndCurriculumIdAndVideoId(@Param("videoId") Long videoId, @Param("userId") Long userId, @Param("curriculumId") Long curriculumId);

  @Query(value = """

      select uc.*
      from user_curriculums uc
      join curriculums c on uc.curriculum_id = c.id  and uc.is_active
      join sessions s  on uc.curriculum_id = c.id and uc.is_active
      join exams e on e.session_id = s.id and e.is_active
      where e.id = :examId
           and uc.user_id  =:userId
           and uc.curriculum_id =:curriculumId
   
   """,nativeQuery = true)
  Optional<UserCurriculum> findByUserIdAndCurriculumIdAndExamId(@Param("examId") Long examId, @Param("userId") Long userId, @Param("curriculumId") Long curriculumId);
}
