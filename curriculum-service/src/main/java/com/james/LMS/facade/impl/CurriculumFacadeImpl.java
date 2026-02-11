package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.*;
import com.james.LMS.entity.*;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.enums.InstructorEnum;
import com.james.LMS.enums.MessageType;
import com.james.LMS.enums.SourceMessageEnum;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.facade.CurriculumFacade;
import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.LoadLecturerIntoCachePayload;
import com.james.LMS.request.CurriculumHomeRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.CurriculumHomeResponse;
import com.james.LMS.response.CurriculumReviewResponse;
import com.james.LMS.service.*;
import com.james.LMS.util.DurationConverterUtil;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurriculumFacadeImpl implements CurriculumFacade {
  private final CurriculumService curriculumService;
  private final TestService testService;
  private final ExamService examService;
  private final VideoService videoService;
  private final SessionService sessionService;
  private final TopicService topicService;
  private final CacheService cacheService;
  private final ProducerLoadLecturesIntoCacheService producerLoadLecturesIntoCacheService;
  private final InstructorService instructorService;

  private static final Integer ZERO_LECTURE = 0;
  private static final Integer INITIAL_HOME_PAGE = 0;

  @Override
  public BaseResponse<CurriculumReviewResponse> findCurriculumForReviewById(Long id) {
    Curriculum curriculum =
        this.curriculumService
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DEMO));
    List<Session> sessions = this.sessionService.findAllByCurriculumId(curriculum.getId());

    List<Long> sessionIds = sessions.stream().map(BaseEntity::getId).toList();

    List<TopicDTO> topicDTOS = this.topicService.findAllTopicDTOByCurriculumId(curriculum.getId());

    List<BaseSessionContentDTO> sessionContentDTOS = this.buildSessionContentDTO(sessionIds);

    Map<Long, List<BaseSessionContentDTO>> collectContentSessionMap =
        sessionContentDTOS.stream()
            .collect(Collectors.groupingBy(BaseSessionContentDTO::getSessionId));

    AtomicReference<Long> totalDurationOfCurriculum = new AtomicReference<>(0L);
    AtomicReference<Integer> totalLectures = new AtomicReference<>(0);

    List<SessionDTO> sessionDTOS =
        this.buildSessionDTO(
            sessions, collectContentSessionMap, totalDurationOfCurriculum, totalLectures);

    return BaseResponse.build(
        CurriculumReviewResponse.builder()
            .title(curriculum.getTitle())
            .headline(curriculum.getHeadLine())
            .cost(curriculum.getCost())
            .description(curriculum.getDescription())
            .name(curriculum.getName())
            .totalTimesStringFormat(
                DurationConverterUtil.toStringDuration(
                    Duration.ofSeconds(totalDurationOfCurriculum.get())))
            .totalSessions(sessionDTOS.size())
            .totalLectures(totalLectures.get())
            .sessionDTOs(sessionDTOS)
            .topicDTOS(topicDTOS)
            .build(),
        true);
  }

  @Override
  public BaseResponse<CurriculumHomeResponse> findCurriculumForHome(
      CurriculumHomeRequest curriculumHomeRequest) {
    log.info("Curriculum Home request {} ", curriculumHomeRequest);
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Long> followedTopicIds =
        this.topicService.findAllTopicIdsByUserId(
            principal.getId(),
            PageRequest.of(INITIAL_HOME_PAGE, curriculumHomeRequest.getTopicSize()));

    Pageable pageable =
        PageRequest.of(
            curriculumHomeRequest.getCurrentPage() - 1, curriculumHomeRequest.getPageSize());
    Page<CurriculumDTO> curriculumDTOPage =
        this.curriculumService.findAllCurriculumsByFollowedTopicIdsOfUser(
            followedTopicIds, pageable);

    List<CurriculumDTO> curriculumDTOS =
        this.addLecturerIntoCurriculum(curriculumDTOPage.stream().toList());

    Map<String, PaginationDTO<CurriculumDTO>> topicNamePaginationDTOMap =
        curriculumDTOS.stream()
            .collect(
                Collectors.groupingBy(
                    CurriculumDTO::getTopicName,
                    Collectors.collectingAndThen(
                        Collectors.toList(),
                        list ->
                            PaginationDTO.<CurriculumDTO>builder()
                                .currentPage(INITIAL_HOME_PAGE)
                                .pageSize(list.size())
                                .totalItems(list.size())
                                .data(list)
                                .build())));
    log.info("Curriculum response");
    return BaseResponse.build(
        CurriculumHomeResponse.builder()
            .topicNamePaginationDTOMap(topicNamePaginationDTOMap)
            .build(),
        true);
  }

  private List<CurriculumDTO> addLecturerIntoCurriculum(List<CurriculumDTO> curriculumDTOS) {

    for (CurriculumDTO curriculumDTO : curriculumDTOS) {
      String instructorKey =
          String.format(InstructorEnum.INSTRUCTOR_KEY.getContent(), curriculumDTO.getUserId());
      log.info("lecturer key {}", instructorKey);
      boolean isAvailableInstructorData = this.cacheService.hasKey(instructorKey);

      if (!isAvailableInstructorData) {
        BaseMessage<LoadLecturerIntoCachePayload> loadLecturerIntoCachePayloadMessage =
            BaseMessage.<LoadLecturerIntoCachePayload>builder()
                .type(MessageType.READ_LECTURER_INFO_AND_CACHE)
                .source(SourceMessageEnum.CURRICULUM_SERVICE)
                .createdAt(Instant.now())
                .payload(
                    LoadLecturerIntoCachePayload.builder()
                        .userId(curriculumDTO.getUserId())
                        .build())
                .build();
        this.producerLoadLecturesIntoCacheService.send(loadLecturerIntoCachePayloadMessage);

        Optional<InstructorDTO> instructorDTOOptional =
            this.instructorService.findByUserId(curriculumDTO.getUserId());
        instructorDTOOptional.ifPresent(curriculumDTO::addLectureInfo);
        continue;
      }

      InstructorDTO lecturerDTO = this.cacheService.retrieveInstructorDTOAndRenewTTL(instructorKey);
      curriculumDTO.addLectureInfo(lecturerDTO);
    }
    return curriculumDTOS;
  }

  private List<BaseSessionContentDTO> buildSessionContentDTO(List<Long> sessionIds) {
    CompletableFuture<List<VideoDTO>> videosFuture =
        CompletableFuture.supplyAsync(() -> this.videoService.findVideoDTOBySessionIds(sessionIds));
    CompletableFuture<List<ExamDTO>> examsFuture =
        CompletableFuture.supplyAsync(() -> this.examService.findExamDTOBySessionIds(sessionIds));
    CompletableFuture.allOf(videosFuture, examsFuture).join();

    List<VideoDTO> videos = videosFuture.join();
    List<ExamDTO> exams = examsFuture.join();

    List<BaseSessionContentDTO> sessionContentDTOS = new ArrayList<>(videos);
    sessionContentDTOS.addAll(exams);
    return sessionContentDTOS;
  }

  private List<SessionDTO> buildSessionDTO(
      List<Session> sessions,
      Map<Long, List<BaseSessionContentDTO>> collectContentSessionMap,
      AtomicReference<Long> totalDurationOfCurriculum,
      AtomicReference<Integer> totalLectures) {
    return sessions.stream()
        .map(
            session -> {
              List<BaseSessionContentDTO> sessionContentDTOSEachSession =
                  collectContentSessionMap.get(session.getId());

              boolean isNoContentOfSession = sessionContentDTOSEachSession == null;
              if (isNoContentOfSession)
                return SessionDTO.builder()
                    .id(session.getId())
                    .index(session.getIndex())
                    .totalLectures(ZERO_LECTURE)
                    .build();

              long totalDurationSeconds =
                  sessionContentDTOSEachSession.stream()
                      .filter(BaseSessionContentDTO::isVideo)
                      .map(VideoDTO.class::cast)
                      .mapToLong(VideoDTO::getDurationSeconds)
                      .sum();

              totalDurationOfCurriculum.updateAndGet(v -> v + totalDurationSeconds);
              totalLectures.updateAndGet(total -> total + sessionContentDTOSEachSession.size());

              SessionDTO sessionDTO =
                  SessionDTO.builder()
                      .id(session.getId())
                      .index(session.getIndex())
                      .totalTimesStringFormat(
                          DurationConverterUtil.toStringDuration(
                              Duration.ofSeconds(totalDurationSeconds)))
                      .totalLectures(sessionContentDTOSEachSession.size())
                      .lectures(sessionContentDTOSEachSession.stream().sorted().toList())
                      .build();

              return sessionDTO;
            })
        .toList();
  }
}
