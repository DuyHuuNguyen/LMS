package com.james.LMS.repository;

import com.james.LMS.dto.UserTopicDTO;
import com.james.LMS.entity.UserTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTopicRepository extends JpaRepository<UserTopic,Long> {
    @Query(value = """
    select ut.topic.id
    from UserTopic ut
    where ut.userId =:userId and ut.isActive = true
    """)
    List<Long> findAllTopicIdsByUserId(Long userId);
}
