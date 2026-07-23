package com.james.LMS.repository;

import com.james.LMS.dto.EtagPartNumberDTO;
import com.james.LMS.entity.UploadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadingSessionRepository extends JpaRepository<UploadingSession, Long> {
    @Query(value = """
    SELECT EXISTS (
        SELECT 1
        FROM part_files pf
        WHERE pf.uploading_session_id = :id
          AND pf.part_number = :nextPartNumber - 1
    )
    """, nativeQuery = true)
    Boolean verifyNextPartNumberBySessionId(Long id, Integer nextPartNumber);

    @Query(value = """
            select new com.james.LMS.dto.EtagPartNumberDTO(fp.etag,fp.partNumber)
                    from FilePart as fp
                     where fp.uploadingSession.id =:id and fp.isActive and fp.uploadingSession.isActive
        """)
    List<EtagPartNumberDTO> findAllById(Long id);
}
