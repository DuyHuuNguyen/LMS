package com.james.LMS.repository;

import com.james.LMS.dto.VideoDTO;
import com.james.LMS.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query(value = """
        select v
        from Video v
        where v.session.id in (:sessionIds)
        """)
    List<Video> findAllBySessionIds(List<Long> sessionIds);
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
    List<VideoDTO> findVideoDTOBySessionId(List<Long> sessionIds);





}
