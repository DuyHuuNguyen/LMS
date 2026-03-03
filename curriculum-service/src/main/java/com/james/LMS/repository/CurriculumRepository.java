package com.james.LMS.repository;

import com.james.LMS.dto.CurriculumDTO;
import com.james.LMS.dto.PurchasedCurriculumDTO;
import com.james.LMS.entity.Curriculum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

      @Query("""
           select distinct new com.james.LMS.dto.CurriculumDTO(
            ch.userId ,
            null ,
            null,
            c.id,
            c.title,
            c.headLine,
            c.cost,
            c.description,
            c.name,
            c.thumbnail,
            t.id,
            t.name
            )
          from Curriculum c
          join CurriculumTopic ct on ct.curriculum.id = c.id
          join Topic t on t.id = ct.topic.id
          join Channel ch on ch.id = c.channel.id
          where t.id in (:followedTopicIds) and c.isActive and t.isActive and ch.isActive and  ct.isActive
      """)
      Page<CurriculumDTO> findAllCurriculumsByFollowedTopicIdsOfUser(List<Long> followedTopicIds, Pageable pageable);


      @Query("""
           select distinct new com.james.LMS.dto.CurriculumDTO(
            ch.userId ,
            null ,
            null,
            c.id,
            c.title,
            c.headLine,
            c.cost,
            c.description,
            c.name,
            c.thumbnail,
            t.id,
            t.name
            )
          from Curriculum c
          join CurriculumTopic ct on ct.curriculum.id = c.id
          join Topic t on t.id = ct.topic.id
          join Channel ch on ch.id = c.channel.id
          where t.id = :topicId and c.isActive and t.isActive and ch.isActive and  ct.isActive
      """)
      Page<CurriculumDTO> findAllCurriculumByTopicId(Long topicId, Pageable pageable);


      Boolean existsCurriculumByIdAndIsActiveIsTrue(Long id);

      Boolean existsByIdAndChannel_UserIdAndIsActiveIsTrue(Long curriculumId,Long userId);


      @Query("""
      select new com.james.LMS.dto.PurchasedCurriculumDTO(
          c.id,
          c.title,
          c.headLine,
          c.description,
          c.thumbnail,
          se.name,
          se.id,
          v.id,
          e.id,
          upr.stoppedAt,
          v.thumbnail,
           CASE
             WHEN upr IS NULL THEN true
             ELSE false
           END
          )
        from Curriculum c
        join UserCurriculum uc on uc.curriculum.id = c.id
        left join UserProgress upr on upr.curriculum.id = c.id
        left join Session se on se.id = upr.session.id
        left join Video v on v.id = upr.video.id
        left join Exam e on e.id = upr.exam.id
        where uc.userId =:userId and c.isActive and uc.isActive and COALESCE(upr.isActive,true) and COALESCE(se.isActive,true) and COALESCE(v.isActive,true) and COALESCE(e.isActive,true)
      """)
      Page<PurchasedCurriculumDTO> findAllPurchasedCurriculums(Long userId,Pageable pageable);


      @Query("""
      select COUNT(uc) > 0
      from UserCurriculum  uc
      join Session s on s.curriculum.id = uc.curriculum.id
      join Video v on v.session.id = s.id
      where uc.userId =:userId and uc.curriculum.id =:curriculumId and s.id =:sessionId and v.id = :videoId and uc.isActive and s.isActive and v.isActive
      """)
      Boolean isPurchasedCurriculumToHaveVideo(Long userId,Long curriculumId,Long sessionId,Long videoId);

      @Query("""
      select COUNT(uc) > 0
      from UserCurriculum  uc
      join Session s on s.curriculum.id = uc.curriculum.id
      join Exam e on e.session.id = s.id
      where uc.userId =:userId and uc.curriculum.id =:curriculumId and s.id =:sessionId and e.id = :examId and uc.isActive and s.isActive and e.isActive
      """)
      Boolean isPurchasedCurriculumToHaveExam(Long userId,Long curriculumId,Long sessionId,Long examId);
}
