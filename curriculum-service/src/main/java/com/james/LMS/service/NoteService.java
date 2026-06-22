package com.james.LMS.service;

import com.james.LMS.dto.NoteDTO;
import com.james.LMS.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {
  void save(Note note);

  Integer findCurrentGlobalIndexByUserIdAndCurriculumId(Long userId, Long curriculumId);

  Page<NoteDTO> findAllByUserIdAndCurriculumIdWithIsActiveIsTrue(Long userId, Long curriculumId, Pageable pageable);
}
