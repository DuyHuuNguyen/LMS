package com.james.LMS.service;

import com.james.LMS.entity.Note;

public interface NoteService {
  void save(Note note);

  Integer findCurrentGlobalIndexByUserIdAndCurriculumId(Long userId, Long curriculumId);
}
