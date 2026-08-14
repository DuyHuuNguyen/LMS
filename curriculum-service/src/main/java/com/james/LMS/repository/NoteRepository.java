package com.james.LMS.repository;

import com.james.LMS.dto.NoteDTO;
import com.james.LMS.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {

    @Query("""
     select COALESCE(MAX(n.globalIndex), 0)
     from Note n
     left join Exam e on e.id = n.exam.id
     left  join Video v on v.id = n.video.id
     left join UserCurriculum uc on uc.userId =:userId and ( uc.curriculum.id = v.session.curriculum.id  or uc.curriculum.id = e.session.curriculum.id)
     where uc is not null and uc.curriculum.id =:curriculumId
    """)
    Integer findCurrentGlobalIndexByUserIdAndCurriculumId(@Param("userId") Long userId, @Param("curriculumId") Long curriculumId);


    @Deprecated
    @Query("""
    select new com.james.LMS.dto.NoteDTO(
        n.id,
        n.globalIndex,
        n.content,
        n.notedAt,
        n.noteType,
        CAST( COALESCE(v.id, n.id) as long)
    )
    from Note n
    left join n.video v
    left join n.exam e
    where n.userId = :userId
      and n.isActive = true
      and (
            v.session.curriculum.id = :curriculumId
         or e.session.curriculum.id = :curriculumId
      )
    order by n.globalIndex
    """)
    Page<NoteDTO> findAllByUserIdAndCurriculumIdWithIsActiveIsTrue(@Param("userId") Long userId, @Param("curriculumId") Long curriculumId, Pageable pageable);
}
