package com.james.LMS.repository;

import com.james.LMS.entity.LastestWatchingVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LastestWatchingVideoRepository extends JpaRepository<LastestWatchingVideo, Long> {


    @Query("""
    SELECT l FROM LastestWatchingVideo l where  l.isActive and l.id =:id
        """)
    Optional<LastestWatchingVideo> findByIdAndActive(Long id);

    @Query("""
        SELECT l
        FROM LastestWatchingVideo l
        WHERE l.isActive AND l.contentId =:videoId AND l.contentType = 'VIDEO'
        """
    )
    Optional<LastestWatchingVideo> findByVideoId(Long videoId);
}
