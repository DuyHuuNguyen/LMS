package com.james.LMS.service.impl;

import com.james.LMS.entity.Note;
import com.james.LMS.repository.NoteRepository;
import com.james.LMS.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
  private final NoteRepository noteRepository;

  @Override
  public void save(Note note) {
    this.noteRepository.save(note);
  }

  @Override
  public Integer findCurrentGlobalIndexByUserIdAndCurriculumId(Long userId, Long curriculumId) {
    return this.noteRepository.findCurrentGlobalIndexByUserIdAndCurriculumId(userId, curriculumId);
  }
}
