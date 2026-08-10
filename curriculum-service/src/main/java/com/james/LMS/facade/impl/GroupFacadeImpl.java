package com.james.LMS.facade.impl;

import com.james.LMS.dto.CurriculumTrainingSetDTO;
import com.james.LMS.dto.TrainingExamDTO;
import com.james.LMS.dto.TrainingSessionDTO;
import com.james.LMS.entity.Group;
import com.james.LMS.entity.TrainingSession;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.facade.GroupFacade;
import com.james.LMS.request.DashBoardRequest;
import com.james.LMS.request.UpsertTrainingSessionRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.DashBoardResponse;
import com.james.LMS.service.CompanyPossessCurriculumService;
import com.james.LMS.service.CompanyService;
import com.james.LMS.service.GroupService;
import com.james.LMS.service.TrainingSessionService;
import com.james.LMS.util.DurationConverterUtil;
import com.james.LMS.util.SecurityUserDetailsUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupFacadeImpl implements GroupFacade {

  private final CompanyService companyService;
  private final GroupService groupService;
  private final TrainingSessionService trainingSessionService;
  private final CompanyPossessCurriculumService companyPossessCurriculumService;

  @SneakyThrows
  @Override
  public BaseResponse<DashBoardResponse> dashBoard(DashBoardRequest request) {
    boolean isAccessibleIntoGroup =
        this.groupService.isUserAccessibleToGroup(
            SecurityUserDetailsUtil.PRINCIPAL.getId(),
            request.getCompanyId(),
            request.getGroupId());

    if (!isAccessibleIntoGroup)
      throw new PermissionDeniedException(ErrorCode.NO_PERMISSION_ACCESS_TO_GROUP);

    CompletableFuture<Integer> totalMembersFuture =
        this.groupService.countMembersByGroupId(request.getGroupId());

    CompletableFuture<List<TrainingSession>> totalTrainingSessionsFuture =
        this.trainingSessionService.findByGroupIdAndStartedAtBetween(
            request.getGroupId(), request.getMonth());

    CompletableFuture.allOf(totalMembersFuture, totalTrainingSessionsFuture).join();

    List<TrainingSessionDTO> trainingSessionDTOS =
        totalTrainingSessionsFuture.get().stream().map(this::toTrainingSessionDTO).toList();

    return BaseResponse.build(
        DashBoardResponse.builder()
            .groupName("James demo group")
            .totalMembers(totalMembersFuture.get())
            .totalTrainingSessions(trainingSessionDTOS.size())
            .trainingSessionDTOS(trainingSessionDTOS)
            .build(),
        true);
  }

  @Override
  @Transactional
  public BaseResponse<Void> createTrainingSession(UpsertTrainingSessionRequest request) {
    boolean isUserAdminGroupAccessibleGroup =
        this.groupService.isGroupAdmin(
            SecurityUserDetailsUtil.PRINCIPAL.getId(), request.getGroupId());
    if (!isUserAdminGroupAccessibleGroup)
      throw new PermissionDeniedException(ErrorCode.NO_PERMISSION_ACCESS_TO_GROUP);

    Group group =
        this.groupService
            .findById(request.getGroupId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.GROUP_NOT_FOUND));

    long startedAt =
        DurationConverterUtil.toEpochMilli(
            request.getStartedAt(), SecurityUserDetailsUtil.PRINCIPAL.getTimeZone());
    long endedAt =
        DurationConverterUtil.toEpochMilli(
            request.getEndedAt(), SecurityUserDetailsUtil.PRINCIPAL.getTimeZone());

    TrainingSession trainingSession =
        TrainingSession.builder()
            .group(group)
            .startedAt(startedAt)
            .endedAt(endedAt)
            .name(request.getTrainingSessionName())
            .build();

    this.trainingSessionService.save(trainingSession);

    return BaseResponse.ok();
  }

  private TrainingSessionDTO toTrainingSessionDTO(TrainingSession trainingSession) {
    var curriculumTrainingSetsDTOs =
        trainingSession.getCurriculumTrainingSets().stream()
            .map(
                curriculumTrainingSet ->
                    CurriculumTrainingSetDTO.builder()
                        .id(curriculumTrainingSet.getId())
                        .name(curriculumTrainingSet.getTrainingSetName())
                        .build())
            .toList();

    var trainingExamDTOS =
        trainingSession.getTrainingExams().stream()
            .map(
                trainingExam ->
                    TrainingExamDTO.builder()
                        .id(trainingExam.getId())
                        .examName(trainingExam.getExamName())
                        .build())
            .toList();

    LocalDateTime startedAt =
        DurationConverterUtil.getLocalDateTimeFromLong(
            trainingSession.getStartedAt(), SecurityUserDetailsUtil.PRINCIPAL.getTimeZone());
    LocalDateTime endedAt =
        DurationConverterUtil.getLocalDateTimeFromLong(
            trainingSession.getEndedAt(), SecurityUserDetailsUtil.PRINCIPAL.getTimeZone());
    boolean isCompleted = endedAt.isBefore(LocalDateTime.now());

    return TrainingSessionDTO.builder()
        .id(trainingSession.getId())
        .name(trainingSession.getName())
        .startedAt(startedAt.toString())
        .endedAt(endedAt.toString())
        .isCompleted(isCompleted)
        .curriculumTrainingSetDTOS(curriculumTrainingSetsDTOs)
        .trainingExamDTOS(trainingExamDTOS)
        .build();
  }
}
