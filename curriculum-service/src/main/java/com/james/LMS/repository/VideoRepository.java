package com.james.LMS.repository;

import com.james.LMS.dto.VideoDTO;
import com.james.LMS.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query(value = """
        select v
        from Video v
        where v.session.id in (:sessionIds)
        """)
    List<Video> findAllBySessionIds(@Param("sessionIds") List<Long> sessionIds);
    @Query("""
       select new com.james.LMS.dto.VideoDTO(
           v.id,
           v.name,
           v.isPreview,
           v.index,
           v.session.id,
            v.durationSeconds
       )
       from Video v
       where v.session.id in :sessionIds
    """)
    List<VideoDTO> findVideoDTOBySessionId(@Param("sessionIds") List<Long> sessionIds);


    @Query("""
    select v.durationSeconds
    from Video v
    where v.id =:id and v.isActive
    """)
    Integer findDurationById(@Param("id") Long id);

    Optional<Video> findByIdentifyCode(String identifyCode);

    @Query("""
    select v
    from Video v
    join fetch v.session
    where v.id =:id and v.isActive
    """)
    Optional<Video> findVideoAndFetchSessionById(@Param("id") Long id);

    Optional<Video> findByIdAndIsActiveIsTrue(Long id);

    @Query("""
    select v
    from Video v
    join fetch v.bucket as b
    where v.id =:id and v.isActive and b.isActive
    """)
    Optional<Video> findByIdAndFetchBucket(@Param("id") Long id);
}
