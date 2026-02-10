package com.james.LMS.repository;

import com.james.LMS.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

  //    @Query("""
  //
  //            select
  //                from Topic t
  //                join CurriculumTopic ct on t.id = ct.topic.id
  //                join Curriculum c on c.id = ct.curriculum.id
  //                join UserCurriculum uc on c.id = uc.curriculum.id
  //                where t.id in (:topicIds)
  //    """)
  //    Pageable<CurriculumDTO> findAllInTopicOfUser(List<Long> topicIds, Pageable pageable);

}
