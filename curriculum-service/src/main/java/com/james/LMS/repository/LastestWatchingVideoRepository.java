package com.james.LMS.repository;

import com.james.LMS.dto.ActiveCurrentSessionDTO;
import com.james.LMS.entity.LastestWatchingVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LastestWatchingVideoRepository extends JpaRepository<LastestWatchingVideo, Long> {


    @Query("""
    SELECT l FROM LastestWatchingVideo l where  l.isActive and l.id =:id
        """)
    Optional<LastestWatchingVideo> findByIdAndActive(@Param("id") Long id);

    @Query("""
        SELECT l
        FROM LastestWatchingVideo l
        WHERE l.isActive AND l.contentId =:videoId AND l.contentType = 'VIDEO'
        """
    )
    Optional<LastestWatchingVideo> findByVideoId(@Param("videoId") Long videoId);


    @Query(
            """
    select new com.james.LMS.dto.ActiveCurrentSessionDTO(l.session.id,l.contentId, l.contentType)
        from LastestWatchingVideo l
              WHERE l.isActive AND l.curriculum.id =:curriculumId AND l.userId =:userId
    """
    )
    Optional<ActiveCurrentSessionDTO> findByUserIdAndCurriculumId(@Param("userId") Long userId, @Param("curriculumId") Long curriculumId );
}
