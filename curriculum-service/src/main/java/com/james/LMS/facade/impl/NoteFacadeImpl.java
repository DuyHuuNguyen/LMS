package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.NoteDTO;
import com.james.LMS.dto.ValidUserPurchasedCurriculumAccessDTO;
import com.james.LMS.entity.Note;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.facade.NoteFacade;
import com.james.LMS.request.NoteCriteria;
import com.james.LMS.request.UpsertNoteRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.NoteResponse;
import com.james.LMS.response.PaginationResponse;
import com.james.LMS.service.CurriculumValidatorService;
import com.james.LMS.service.ExamService;
import com.james.LMS.service.NoteService;
import com.james.LMS.service.VideoService;
import com.james.LMS.util.DurationConverterUtil;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    this.validateUserAccess(userId, curriculumId);

    int globalIndex = getNextGlobalIndex(userId, curriculumId);

    Note.NoteBuilder builder = buildBaseNote(request, globalIndex, principal.getId());

    this.attachContent(builder, request);

    this.noteService.save(builder.build());

    return BaseResponse.ok();
  }

    @Override
    public BaseResponse<PaginationResponse<NoteResponse>> findAllNote(NoteCriteria noteCriteria) {
        SecurityUserDetails principal =
                (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId = principal.getId();
        Long curriculumId = noteCriteria.getCurriculumId();
        this.validateUserAccess(userId, curriculumId);

        Pageable pageable =
            PageRequest.of(
                noteCriteria.getCurrentPage() - 1,
                noteCriteria.getPageSize());
        Page<NoteDTO> noteDTOPage =
            this.noteService.findAllByUserIdAndCurriculumIdWithIsActiveIsTrue(
                userId, curriculumId, pageable);

        List<NoteResponse> noteResponses =
            noteDTOPage
                .stream()
                .map(
                    noteDTO ->
                        NoteResponse.builder()
                            .id(noteDTO.getId())
                            .globalIndex(noteDTO.getGlobalIndex())
                            .content(noteDTO.getContent())
                            .notedAt(
                                DurationConverterUtil.toStringDuration(
                                    Duration.ofSeconds(noteDTO.getNotedAt())))
                            .noteType(noteDTO.getNoteType())
                            .sessionContentId(noteDTO.getSessionContentId())
                            .build())
                .toList();

        return BaseResponse.build(
            PaginationResponse.<NoteResponse>builder()
                .data(noteResponses)
                .currentPage(noteCriteria.getCurrentPage())
                .totalPages(noteDTOPage.getTotalPages())
                .totalElements(noteDTOPage.getNumberOfElements())
                .build(),
            true);
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

  private Note.NoteBuilder buildBaseNote(UpsertNoteRequest request, int globalIndex, Long userId) {
    return Note.builder()
        .globalIndex(globalIndex)
        .content(request.getContent())
        .notedAt(request.getNotedAt())
            .userId(userId)
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
