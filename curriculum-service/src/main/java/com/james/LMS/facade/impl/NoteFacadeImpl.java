package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.ValidUserPurchasedCurriculumAccessDTO;
import com.james.LMS.entity.Note;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.facade.NoteFacade;
import com.james.LMS.request.UpsertNoteRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.service.CurriculumValidatorService;
import com.james.LMS.service.ExamService;
import com.james.LMS.service.NoteService;
import com.james.LMS.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteFacadeImpl implements NoteFacade {
  private final VideoService videoService;
  private final ExamService examService;
  private final NoteService noteService;
  private final CurriculumValidatorService curriculumValidatorService;

  private static final int NEXT_GLOBAL_INDEX = 1;

  @Override
  @Transactional
  public BaseResponse<Void> createNote(UpsertNoteRequest request) {

    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Long userId = principal.getId();
    Long curriculumId = request.getCurriculumId();

    validateUserAccess(userId, curriculumId);

    int globalIndex = getNextGlobalIndex(userId, curriculumId);

    Note.NoteBuilder builder = buildBaseNote(request, globalIndex);

    attachContent(builder, request);

    noteService.save(builder.build());

    return BaseResponse.ok();
  }

  private void validateUserAccess(Long userId, Long curriculumId) {
    ValidUserPurchasedCurriculumAccessDTO dto =
        ValidUserPurchasedCurriculumAccessDTO.builder()
            .userId(userId)
            .curriculumId(curriculumId)
            .build();

    boolean isValid = curriculumValidatorService.isPurchasedCurriculum(dto);

    if (!isValid) {
      throw new PermissionDeniedException(ErrorCode.CURRICULUM_NOT_FOUND);
    }
  }

  private int getNextGlobalIndex(Long userId, Long curriculumId) {
    return noteService.findCurrentGlobalIndexByUserIdAndCurriculumId(userId, curriculumId)
        + NEXT_GLOBAL_INDEX;
  }

  private Note.NoteBuilder buildBaseNote(UpsertNoteRequest request, int globalIndex) {
    return Note.builder()
        .globalIndex(globalIndex)
        .content(request.getContent())
        .notedAt(request.getNotedAt())
        .noteType(request.getNoteType());
  }

  private void attachContent(Note.NoteBuilder builder, UpsertNoteRequest request) {

    switch (request.getNoteType()) {
      case EXAM_NOTE:
        builder.exam(
            examService
                .findByIdAndIsActiveIsTrue(request.getSessionContentId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.EXAM_NOT_FOUND)));
        break;

      case VIDEO_NOTE:
        builder.video(
            videoService
                .findByIdAndIsActiveIsTrue(request.getSessionContentId())
                .orElseThrow(
                    () -> new EntityNotFoundException(ErrorCode.VIDEO_METADATA_NOT_FOUND)));
        break;

      default:
        throw new PermissionDeniedException(ErrorCode.CURRICULUM_NOT_FOUND);
    }
  }
}
