package com.james.LMS.repository;

import com.james.LMS.dto.*;
import com.james.LMS.entity.Curriculum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
            c.requirement,
            c.thumbnail,
            t.id,
            t.name,
            CASE
             WHEN w IS NULL THEN false 
             ELSE true
           END
            )
          from Curriculum c
          join CurriculumTopic ct on ct.curriculum.id = c.id
          join Topic t on t.id = ct.topic.id
          join Channel ch on ch.id = c.channel.id
                left join Wishlist  w on w.userId =:userId and w.curriculum.id = c.id
          where t.id in (:followedTopicIds) and c.isActive and t.isActive and ch.isActive and  ct.isActive 
      """)
      Page<CurriculumDTO> findAllCurriculumsByFollowedTopicIdsOfUser(@Param("followedTopicIds") List<Long> followedTopicIds, @Param("userId") Long userId, Pageable pageable);


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
            c.requirement,
            c.thumbnail,
            t.id,
            t.name, 
            CASE
             WHEN wl IS NULL THEN false 
             ELSE wl.isActive
           END
            )
          from Curriculum c
          join CurriculumTopic ct on ct.curriculum.id = c.id
          join Topic t on t.id = ct.topic.id
          join Channel ch on ch.id = c.channel.id
          left join Wishlist  wl on wl.curriculum.id = c.id and wl.userId =:userId
          where t.id = :topicId and c.isActive and t.isActive and ch.isActive and  ct.isActive 
      """)
      Page<CurriculumDTO> findAllCurriculumByTopicId(@Param("topicId") Long topicId, @Param("userId") Long userId, Pageable pageable);


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
      Page<PurchasedCurriculumDTO> findAllPurchasedCurriculums(@Param("userId") Long userId,Pageable pageable);


      @Query("""
      select COUNT(uc) > 0
      from UserCurriculum  uc
      join Session s on s.curriculum.id = uc.curriculum.id
      join Video v on v.session.id = s.id
      where uc.userId =:userId and uc.curriculum.id =:curriculumId and s.id =:sessionId and v.id = :videoId and uc.isActive and s.isActive and v.isActive
      """)
      Boolean isPurchasedCurriculumToHaveVideo(@Param("userId") Long userId, @Param("curriculumId") Long curriculumId, @Param("sessionId") Long sessionId, @Param("videoId") Long videoId);

      @Query("""
      select COUNT(uc) > 0
      from UserCurriculum  uc
      join Session s on s.curriculum.id = uc.curriculum.id
      join Exam e on e.session.id = s.id
      where uc.userId =:userId and uc.curriculum.id =:curriculumId and s.id =:sessionId and e.id = :examId and uc.isActive and s.isActive and e.isActive
      """)
      Boolean isPurchasedCurriculumToHaveExam(@Param("userId") Long userId, @Param("curriculumId") Long curriculumId, @Param("sessionId") Long sessionId, @Param("examId") Long examId);



      @Query("""
      select c
      from Curriculum c
      join fetch c.channel
      where c.id =:id and c.isActive and c.channel.isActive
      """)
      Optional<Curriculum> findByIdFetchChannel(@Param("id") Long id);



      @Query("""
      select count(c) > 0
      from Curriculum c
      join Channel  ch on c.channel.id = ch.id
      join Session se on se.curriculum.id = c.id
      where c.id =:curriculumId and c.isActive and ch.userId =:userHolderChannelId and ch.isActive and se.id =:sessionId and se.isActive
      """)
      Boolean isExistedChannelAndCurriculumForUploadVideo(@Param("userHolderChannelId") Long userHolderChannelId, @Param("curriculumId") Long curriculumId, @Param("sessionId") Long sessionId);



      @Query("""
            select COUNT(c) > 0
            from Curriculum c
            join Session se on se.curriculum.id = c.id
            join Video  v on v.session.id = se.id
            where c.channel.userId =:userHolderChannelId and c.isActive and c.id =:curriculumId and se.isActive and v.id = :videoId and v.isActive
            """)
      Boolean isInstructorHoldVideo(@Param("userHolderChannelId") Long userHolderChannelId, @Param("curriculumId") Long curriculumId, @Param("videoId") Long videoId);


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
            c.requirement,
            c.thumbnail,
            t.id,
            t.name,
            wl.isActive
            )
          from Curriculum c
          join CurriculumTopic ct on ct.curriculum.id = c.id
          join Topic t on t.id = ct.topic.id
          join Channel ch on ch.id = c.channel.id
          join Wishlist wl on wl.curriculum.id = c.id
          where wl.userId =:userId and wl.isActive and c.isActive and t.isActive and ch.isActive and  ct.isActive
      """)
      Page<CurriculumDTO> findAllWishlist(@Param("userId") Long userId, Pageable pageable);


      @Query("""
           select distinct new com.james.LMS.dto.WishListCurriculumDTO(
            c.id,
            wl.id,
            c.title,
            c.headLine,
            c.cost,
            c.description,
            c.requirement,
            c.thumbnail,
            t.id,
            t.name
            )
          from Curriculum c
          join CurriculumTopic ct on ct.curriculum.id = c.id
          join Topic t on t.id = ct.topic.id
          join Channel ch on ch.id = c.channel.id
          join Wishlist wl on wl.curriculum.id = c.id
          where wl.userId =:userId and wl.isActive and c.isActive and t.isActive and ch.isActive and  ct.isActive
      """)
      Page<WishListCurriculumDTO> findAllWishlistCurriculum(@Param("userId") Long userId,Pageable pageable);

      @Query("""
           select  new com.james.LMS.dto.CurriculumChannelDTO(
            c.id,
            c.title,
            c.headLine,
            c.cost,
            c.description,
            c.requirement,
            c.thumbnail,
             size(c.sessions),
              (
                    select coalesce(sum(v.durationSeconds),0)
                    from Video v
                    where v.session.curriculum.id = c.id and v.isActive
              )
            )
          from Curriculum c
          join Channel ch on ch.id = c.channel.id
          where c.isActive and ch.isActive and ch.id =:channelId
      """)
      Page<CurriculumChannelDTO> findAllInChannel(@Param("channelId") Long channelId,Pageable pageable);


      @Query("""
          select count(uc) > 0
          from UserCurriculum uc
          join Curriculum c on c.id = uc.curriculum.id
          where c.isActive = true
            and uc.isActive = true
            and uc.userId = :#{#dto.userId}
            and c.id = :#{#dto.curriculumId}
      """)
      Boolean isPurchasedCurriculum(@Param("dto") ValidUserPurchasedCurriculumAccessDTO dto);


      @Query(
          value =
              """
            SELECT
              ch.user_id AS userId,
              c.id AS id,
              c.title AS title,
              c.head_line AS headLine,
              c.cost AS cost,
              c.description AS description,
              c.requirement AS requirement,
              c.thumbnail AS thumbnail,
              ca.total_duration_seconds AS totalDurationSeconds,
              CASE
                WHEN :keyword IS NULL OR btrim(:keyword) = '' THEN 0
                ELSE ts_rank(
                  setweight(to_tsvector('simple', COALESCE(c.title, '')), 'A') ||
                  setweight(to_tsvector('simple', COALESCE(c.head_line, '')), 'B') ||
                  setweight(to_tsvector('simple', COALESCE(c.description, '')), 'C'),
                  plainto_tsquery('simple', :keyword)
                )
              END AS relevanceScore
            FROM curriculums c
            JOIN channels ch ON ch.id = c.channel_id
            JOIN curriculum_audits ca ON ca.curriculum_id = c.id
            WHERE c.is_active = true
              AND ch.is_active = true
              AND ca.is_active = true
              AND ca.total_duration_seconds >= :totalDurationSeconds
              AND (
                :applyTopicFilter = false
                OR EXISTS (
                  SELECT 1
                  FROM curriculum_topics ct
                  JOIN topics t ON t.id = ct.topic_id
                  WHERE ct.curriculum_id = c.id
                    AND ct.is_active = true
                    AND t.is_active = true
                    AND t.id IN (:topicIds)
                )
              )
              AND (
                :keyword IS NULL
                OR btrim(:keyword) = ''
                OR (
                  setweight(to_tsvector('simple', COALESCE(c.title, '')), 'A') ||
                  setweight(to_tsvector('simple', COALESCE(c.head_line, '')), 'B') ||
                  setweight(to_tsvector('simple', COALESCE(c.description, '')), 'C')
                ) @@ plainto_tsquery('simple', :keyword)
              )
            ORDER BY relevanceScore DESC, c.created_at DESC
      """,
          countQuery =
              """
            SELECT COUNT(1)
            FROM curriculums c
            JOIN channels ch ON ch.id = c.channel_id
            JOIN curriculum_audits ca ON ca.curriculum_id = c.id
            WHERE c.is_active = true
              AND ch.is_active = true
              AND ca.is_active = true
              AND ca.total_duration_seconds >= :totalDurationSeconds
              AND (
                :applyTopicFilter = false
                OR EXISTS (
                  SELECT 1
                  FROM curriculum_topics ct
                  JOIN topics t ON t.id = ct.topic_id
                  WHERE ct.curriculum_id = c.id
                    AND ct.is_active = true
                    AND t.is_active = true
                    AND t.id IN (:topicIds)
                )
              )
              AND (
                :keyword IS NULL
                OR btrim(:keyword) = ''
                OR (
                  setweight(to_tsvector('simple', COALESCE(c.title, '')), 'A') ||
                  setweight(to_tsvector('simple', COALESCE(c.head_line, '')), 'B') ||
                  setweight(to_tsvector('simple', COALESCE(c.description, '')), 'C')
                ) @@ plainto_tsquery('simple', :keyword)
              )
      """,
          nativeQuery = true)
      Page<CurriculumSearchDTO> findAllByCriteria(
          @Param("keyword") String keyword,
          @Param("totalDurationSeconds") Long totalDurationSeconds,
          @Param("topicIds") Set<Long> topicIds,
          @Param("applyTopicFilter") boolean applyTopicFilter,
          Pageable pageable);
}
