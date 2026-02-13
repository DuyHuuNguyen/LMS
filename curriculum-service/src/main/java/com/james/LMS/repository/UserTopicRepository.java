package com.james.LMS.repository;

import com.james.LMS.dto.UserTopicDTO;
import com.james.LMS.entity.UserTopic;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    Boolean existsByUserIdAndTopic_IdAndIsActiveIsTrue(Long userId,Long topicId);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE user_topics
        SET is_active = false
        WHERE user_id = :userId
        AND topic_id IN (:topicIds)
        """, nativeQuery = true)
    int unfollowTopics(@Param("topicIds") List<Long> topicIds, @Param("userId") Long userId);

}
