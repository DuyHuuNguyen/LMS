package com.james.LMS.repository;

import com.james.LMS.dto.TopicDTO;
import com.james.LMS.entity.Topic;
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
    where ct.curriculum.id = :curriculumId
    """)
    List<TopicDTO> findAllTopicDTOByCurriculumId(Long curriculumId);

}
