package com.james.LMS.repository;

import com.james.LMS.dto.TopicDTO;
import com.james.LMS.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query(value = """
    select new com.james.LMS.dto.TopicDTO(
        t.id,
        t.name
        )
    from Topic t
    join CurriculumTopic  ct on ct.topic.id = t.id
    where ct.curriculum.id = :curriculumId and t.isActive = true and ct.isActive = true
    """)
    List<TopicDTO> findAllTopicDTOByCurriculumId(Long curriculumId);


    @Query(value = """
    select ut.topic.id
    from UserTopic ut
    where ut.userId =:userId and ut.isActive = true
    """)
    List<Long> findAllTopicIdsByUserId(Long userId, Pageable pageable);

    @Query(value = """
    select new com.james.LMS.dto.TopicDTO(
        t.id,
        t.name
        )
    from Topic t
    where t.isActive = true
    """)
    Page<TopicDTO> findAllTopicDTOs(Pageable pageable);
}
