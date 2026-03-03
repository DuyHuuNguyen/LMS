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
import com.james.LMS.request.CurriculumByTopicRequest;
import com.james.LMS.request.CurriculumHomeRequest;
import com.james.LMS.request.PurchasedCurriculumCriteria;
import com.james.LMS.request.TopicCriteria;
import com.james.LMS.response.*;
import com.james.LMS.service.*;
import com.james.LMS.util.DurationConverterUtil;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
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
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CURRICULUM_NOT_FOUND));
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
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Long> followedTopicIds =
        this.topicService.findAllTopicIdsByUserId(
            principal.getId(),
            PageRequest.of(INITIAL_HOME_PAGE, curriculumHomeRequest.getTopicSize()));
    boolean isNotSelectTopics = followedTopicIds == null || followedTopicIds.isEmpty();
    if (isNotSelectTopics) throw new EntityNotFoundException(ErrorCode.USER_TOPIC_NOT_FOUND);

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

  @Override
  public BaseResponse<PaginationResponse<TopicResponse>> findAllTopicByCriteria(
      TopicCriteria topicCriteria) {
    log.info("Query DB for topics...");
    Pageable pageable =
        PageRequest.of(topicCriteria.getCurrentPage() - 1, topicCriteria.getPageSize());
    Page<TopicDTO> topicDTOPage = this.topicService.findAll(pageable);
    List<TopicResponse> topicResponses =
        topicDTOPage
            .get()
            .map(
                topicDTO ->
                    TopicResponse.builder().id(topicDTO.getId()).name(topicDTO.getName()).build())
            .toList();

    return BaseResponse.build(
        PaginationResponse.<TopicResponse>builder()
            .currentPage(topicCriteria.getCurrentPage())
            .totalPages(topicDTOPage.getTotalPages())
            .totalElements(topicDTOPage.getNumberOfElements())
            .data(topicResponses)
            .build(),
        true);
  }

  @Override
  public BaseResponse<CurriculumHomeResponse> findCurriculumForHomeNewFlow(
      CurriculumHomeRequest curriculumHomeRequest) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Long> followedTopicIds =
        this.topicService.findAllTopicIdsByUserId(
            principal.getId(),
            PageRequest.of(INITIAL_HOME_PAGE, curriculumHomeRequest.getTopicSize()));
    boolean isNotSelectTopics = followedTopicIds == null || followedTopicIds.isEmpty();
    if (isNotSelectTopics) throw new EntityNotFoundException(ErrorCode.USER_TOPIC_NOT_FOUND);

    Map<String, PaginationDTO<CurriculumDTO>> topicNameCurriculumDTOMap = new HashMap<>();
    Pageable pageable =
        PageRequest.of(
            curriculumHomeRequest.getCurrentPage() - 1, curriculumHomeRequest.getPageSize());

    for (var topicId : followedTopicIds) {
      Page<CurriculumDTO> curriculumDTOPage =
          this.curriculumService.findAllCurriculumByTopicId(topicId, pageable);

      boolean isNotFoundCurriculum = curriculumDTOPage.isEmpty();
      if (isNotFoundCurriculum) continue;

      List<CurriculumDTO> curriculumDTOS =
          this.addLecturerIntoCurriculum(curriculumDTOPage.toList());
      String topicName = curriculumDTOS.getFirst().getTopicName();
      PaginationDTO<CurriculumDTO> curriculumDTOPaginationDTO =
          PaginationDTO.<CurriculumDTO>builder()
              .totalItems(curriculumDTOS.size())
              .currentPage(curriculumHomeRequest.getCurrentPage())
              .pageSize(curriculumHomeRequest.getPageSize())
              .data(curriculumDTOS)
              .build();

      topicNameCurriculumDTOMap.put(topicName, curriculumDTOPaginationDTO);
    }

    return BaseResponse.build(
        CurriculumHomeResponse.builder()
            .topicNamePaginationDTOMap(topicNameCurriculumDTOMap)
            .build(),
        true);
  }

  @Override
  public BaseResponse<PaginationResponse<CurriculumResponse>> findCurriculumByTopicId(
      CurriculumByTopicRequest curriculumByTopicRequest) {
    log.info("query {}", curriculumByTopicRequest);
    boolean isExistsTopic = this.topicService.existsById(curriculumByTopicRequest.getTopicId());

    if (!isExistsTopic) throw new EntityNotFoundException(ErrorCode.USER_TOPIC_NOT_FOUND);

    Pageable pageable =
        PageRequest.of(
            curriculumByTopicRequest.getCurrentPage() - 1, curriculumByTopicRequest.getPageSize());
    Page<CurriculumDTO> curriculumDTOPage =
        this.curriculumService.findAllCurriculumByTopicId(
            curriculumByTopicRequest.getTopicId(), pageable);

    List<CurriculumResponse> curriculumResponses =
        this.addLecturerIntoCurriculum(curriculumDTOPage.toList()).stream()
            .map(
                curriculumDTO ->
                    CurriculumResponse.builder()
                        .userId(curriculumDTO.getUserId())
                        .username(curriculumDTO.getUsername())
                        .avatar(curriculumDTO.getAvatar())
                        .id(curriculumDTO.getId())
                        .title(curriculumDTO.getTitle())
                        .headLine(curriculumDTO.getHeadLine())
                        .cost(curriculumDTO.getCost())
                        .description(curriculumDTO.getDescription())
                        .name(curriculumDTO.getName())
                        .thumbnail(curriculumDTO.getThumbnail())
                        .topicId(curriculumDTO.getTopicId())
                        .topicName(curriculumDTO.getTopicName())
                        .build())
            .toList();

    return BaseResponse.build(
        PaginationResponse.<CurriculumResponse>builder()
            .data(curriculumResponses)
            .currentPage(curriculumByTopicRequest.getCurrentPage())
            .totalPages(curriculumDTOPage.getTotalPages())
            .totalElements(curriculumDTOPage.getNumberOfElements())
            .build(),
        true);
  }

  @Override
  public BaseResponse<PaginationResponse<PurchasedCurriculumResponse>> findAllPurchasedCurriculums(
      PurchasedCurriculumCriteria purchasedCurriculumCriteria) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Pageable pageable =
        PageRequest.of(
            purchasedCurriculumCriteria.getCurrentPage() - 1,
            purchasedCurriculumCriteria.getPageSize());

    Page<PurchasedCurriculumDTO> purchasedCurriculumDTOPage =
        this.curriculumService.findAllPurchasedCurriculums(principal.getId(), pageable);

    List<PurchasedCurriculumResponse> purchasedCurriculumResponses =
        purchasedCurriculumDTOPage.stream()
            .map(
                purchasedCurriculumDTO ->
                    PurchasedCurriculumResponse.builder()
                        .id(purchasedCurriculumDTO.getId())
                        .title(purchasedCurriculumDTO.getTitle())
                        .headline(purchasedCurriculumDTO.getHeadLine())
                        .description(purchasedCurriculumDTO.getDescription())
                        .curriculumThumbnail(purchasedCurriculumDTO.getCurriculumThumbnail())
                        .isFirstTimeLearnCurriculum(
                            purchasedCurriculumDTO.getIsFirstTimeLearnCurriculum())
                        .sessionName(purchasedCurriculumDTO.getSessionName())
                        .sessionId(purchasedCurriculumDTO.getSessionId())
                        .sessionContentId(
                            Objects.nonNull(purchasedCurriculumDTO.getVideoId())
                                ? purchasedCurriculumDTO.getVideoId()
                                : purchasedCurriculumDTO.getExamId())
                        .isVideo(Objects.nonNull(purchasedCurriculumDTO.getVideoId()))
                        .stoppedAt(
                            Objects.nonNull(purchasedCurriculumDTO.getStoppedAt())
                                ? DurationConverterUtil.toStringDuration(
                                    Duration.ofSeconds(purchasedCurriculumDTO.getStoppedAt()))
                                : null)
                        .build())
            .toList();

    return BaseResponse.build(
        PaginationResponse.<PurchasedCurriculumResponse>builder()
            .totalPages(purchasedCurriculumDTOPage.getTotalPages())
            .totalElements(purchasedCurriculumDTOPage.getNumberOfElements())
            .currentPage(purchasedCurriculumCriteria.getCurrentPage())
            .data(purchasedCurriculumResponses)
            .build(),
        true);
  }

  private List<CurriculumDTO> addLecturerIntoCurriculum(List<CurriculumDTO> curriculumDTOS) {

    for (CurriculumDTO curriculumDTO : curriculumDTOS) {
      String instructorKey =
          String.format(InstructorEnum.INSTRUCTOR_KEY.getContent(), curriculumDTO.getUserId());
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
