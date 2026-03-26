package com.james.LMS.repository;

import com.james.LMS.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    Integer findCurrentGlobalIndexByUserIdAndCurriculumId(Long userId,Long curriculumId);
}
