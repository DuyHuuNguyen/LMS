package com.james.LMS.service.impl;

import com.james.LMS.dto.NoteDTO;
import com.james.LMS.entity.Note;
import com.james.LMS.repository.NoteRepository;
import com.james.LMS.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Override
  public Page<NoteDTO> findAllByUserIdAndCurriculumIdWithIsActiveIsTrue(Long userId, Long curriculumId, Pageable pageable) {
    return this.noteRepository.findAllByUserIdAndCurriculumIdWithIsActiveIsTrue(userId,curriculumId,pageable);
  }

}
