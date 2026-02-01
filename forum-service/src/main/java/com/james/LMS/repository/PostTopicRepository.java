package com.james.LMS.repository;

import com.james.LMS.entity.PostTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTopicRepository extends JpaRepository<PostTopic,Long> {
}
