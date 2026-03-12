package com.james.LMS.repository;

import com.james.LMS.entity.Channel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

  Optional<Channel> findChannelByUserId(Long userId);
}
