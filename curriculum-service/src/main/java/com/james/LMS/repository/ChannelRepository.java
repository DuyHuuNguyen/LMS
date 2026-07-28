package com.james.LMS.repository;

import com.james.LMS.entity.Channel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

  Optional<Channel> findChannelByUserId(Long userId);

  @Query(value = """
      select exists (
            select c
            from Channel c
            where c.isActive and c.id =:channelId and c.userId =:userId
            )
      """)
  Boolean verifyChannelOfLecturer(Long userId, Long channelId);
}
