package com.james.LMS.repository;

import com.james.LMS.dto.CurriculumDTO;
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
}
