package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.*;
import com.james.LMS.entity.*;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.enums.InstructorEnum;
import com.james.LMS.enums.MessageType;
import com.james.LMS.enums.SourceMessageEnum;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.facade.CurriculumFacade;
import com.james.LMS.message.BaseMessage;
import com.james.LMS.message.LoadLecturerIntoCachePayload;
import com.james.LMS.request.*;
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
import org.springframework.transaction.annotation.Transactional;

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
  private final ChannelService channelService;
  private final WishlistService wishlistService;
  private final CurriculumValidatorService curriculumValidatorService;

  private static final Integer ZERO_LECTURE = 0;
  private static final Integer INITIAL_HOME_PAGE = 0;

  @Override
  public BaseResponse<CurriculumReviewResponse> findCurriculumForReviewById(Long id) {
    Curriculum curriculum =
        this.curriculumService
            .findByIdAndFetchChannel(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CURRICULUM_NOT_FOUND));
    CompletableFuture<List<Session>> sessionsFuture =
        CompletableFuture.supplyAsync(
            () -> this.sessionService.findAllByCurriculumId(curriculum.getId()));

    CompletableFuture<List<TopicDTO>> topicDTOSFuture =
        CompletableFuture.supplyAsync(
            () -> this.topicService.findAllTopicDTOByCurriculumId(curriculum.getId()));

    CompletableFuture<Map<Long, List<BaseSessionContentDTO>>> collectContentSessionMapFuture =
        sessionsFuture.thenApplyAsync(
            sessions -> {
              List<Long> sessionIds = sessions.stream().map(BaseEntity::getId).toList();
              List<BaseSessionContentDTO> sessionContentDTOS =
                  this.buildSessionContentDTO(sessionIds);
              return sessionContentDTOS.stream()
                  .collect(Collectors.groupingBy(BaseSessionContentDTO::getSessionId));
            });

    CompletableFuture<InstructorDTO> instructorDTOFuture =
        CompletableFuture.supplyAsync(
            () ->
                this.instructorService
                    .findByUserId(curriculum.getChannel().getUserId())
                    .orElseThrow(
                        () -> new EntityNotFoundException(ErrorCode.INSTRUCTOR_NOT_FOUND)));

    CompletableFuture.allOf(
            sessionsFuture, topicDTOSFuture, collectContentSessionMapFuture, instructorDTOFuture)
        .join();

    List<Session> sessions = sessionsFuture.join();
    List<TopicDTO> topicDTOS = topicDTOSFuture.join();
    Map<Long, List<BaseSessionContentDTO>> collectContentSessionMap =
        collectContentSessionMapFuture.join();
    InstructorDTO instructorDTO = instructorDTOFuture.join();

    AtomicReference<Long> totalDurationOfCurriculum = new AtomicReference<>(0L);
    AtomicReference<Integer> totalLectures = new AtomicReference<>(0);

    List<SessionDTO> sessionDTOS =
        this.buildSessionDTO(
            sessions, collectContentSessionMap, totalDurationOfCurriculum, totalLectures);

    return BaseResponse.build(
        CurriculumReviewResponse.builder()
            .instructorDTO(instructorDTO)
            .title(curriculum.getTitle())
            .headline(curriculum.getHeadLine())
            .cost(curriculum.getCost())
            .description(curriculum.getDescription())
            .requirement(curriculum.getRequirement())
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
        this.buildCurriculumResponses(curriculumDTOPage.toList());

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

  @Override
  public BaseResponse<PaginationResponse<WishListCurriculumResponse>> findAllWishlist(
      WishlistRequest wishlistRequest) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Pageable pageable =
        PageRequest.of(wishlistRequest.getCurrentPage() - 1, wishlistRequest.getPageSize());
    Page<WishListCurriculumDTO> curriculumDTOPage =
        this.curriculumService.findAllWishlistCurriculum(principal.getId(), pageable);

    List<WishListCurriculumResponse> curriculumResponses =
        curriculumDTOPage.stream()
            .map(
                wishlistCurriculumDTO ->
                    WishListCurriculumResponse.builder()
                        .id(wishlistCurriculumDTO.getId())
                        .wishlistId(wishlistCurriculumDTO.getWishlistId())
                        .title(wishlistCurriculumDTO.getTitle())
                        .headLine(wishlistCurriculumDTO.getHeadLine())
                        .cost(wishlistCurriculumDTO.getCost())
                        .description(wishlistCurriculumDTO.getDescription())
                        .requirement(wishlistCurriculumDTO.getRequirement())
                        .thumbnail(wishlistCurriculumDTO.getThumbnail())
                        .topicId(wishlistCurriculumDTO.getTopicId())
                        .topicName(wishlistCurriculumDTO.getTopicName())
                        .build())
            .toList();

    return BaseResponse.build(
        PaginationResponse.<WishListCurriculumResponse>builder()
            .data(curriculumResponses)
            .currentPage(wishlistRequest.getCurrentPage())
            .totalPages(curriculumDTOPage.getTotalPages())
            .totalElements(curriculumDTOPage.getNumberOfElements())
            .build(),
        true);
  }

  @Override
  @Transactional
  public BaseResponse<Void> addWishList(Long id) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    boolean isExistWishlist =
        this.wishlistService.isExistByCurriculumIdAndUserId(id, principal.getId());
    if (isExistWishlist) throw new PermissionDeniedException(ErrorCode.CREATED_WISH_LIST);

    Curriculum curriculum =
        this.curriculumService
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CURRICULUM_NOT_FOUND));
    Wishlist wishlist = Wishlist.builder().userId(principal.getId()).curriculum(curriculum).build();
    this.wishlistService.save(wishlist);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<Void> removeWishlist(RemoveWishlistRequest request) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Wishlist wishlist =
        this.wishlistService
            .findById(request.getWishlistId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.WISHLIST_NOT_FOUND));

    boolean isOwnerWishlist = wishlist.getUserId().equals(principal.getId());
    if (!isOwnerWishlist) throw new PermissionDeniedException(ErrorCode.WISHLIST_NOT_FOUND);

    wishlist.softDelete();
    this.wishlistService.save(wishlist);

    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<SessionDetailResponse> findSessionsOfCurriculum(Long id) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    ValidUserPurchasedCurriculumAccessDTO validUserPurchasedCurriculumAccessDTO =
        ValidUserPurchasedCurriculumAccessDTO.builder()
            .userId(principal.getId())
            .curriculumId(id)
            .build();
    boolean isPurchasedCurriculum =
        this.curriculumValidatorService.isPurchasedCurriculum(
            validUserPurchasedCurriculumAccessDTO);

    if (!isPurchasedCurriculum) throw new PermissionDeniedException(ErrorCode.CURRICULUM_NOT_FOUND);

    List<Session> sessions =
        this.sessionService.findAllSessionAndFetchVideosAndExamsByCurriculumId(id);

    SessionDetailResponse sessionDetailResponse =
        SessionDetailResponse.builder().sessionDTOS(this.mapSessionsToSessionDTO(sessions)).build();
    return BaseResponse.build(sessionDetailResponse, true);
  }

  @Override
  public BaseResponse<PaginationResponse<SearchCurriculumResponse>> findAllByCriteria(
      CurriculumCriteria curriculumCriteria) {
    int currentPage =
        Objects.nonNull(curriculumCriteria) && Objects.nonNull(curriculumCriteria.getCurrentPage())
            ? curriculumCriteria.getCurrentPage()
            : 1;
    int pageSize =
        Objects.nonNull(curriculumCriteria) && Objects.nonNull(curriculumCriteria.getPageSize())
            ? curriculumCriteria.getPageSize()
            : 10;
    String keyword = Objects.nonNull(curriculumCriteria) ? curriculumCriteria.getKeyword() : null;
    long totalDurationSeconds =
        Objects.nonNull(curriculumCriteria) && Objects.nonNull(curriculumCriteria.getDuration())
            ? curriculumCriteria.getDuration()
            : 0L;
    Set<Long> requestedTopicIds =
        Objects.nonNull(curriculumCriteria) ? curriculumCriteria.getTopicIds() : null;
    boolean applyTopicFilter = Objects.nonNull(requestedTopicIds) && !requestedTopicIds.isEmpty();
    Set<Long> topicIds = applyTopicFilter ? requestedTopicIds : Set.of(-1L);

    Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

    Page<CurriculumSearchDTO> curriculumSearchDTOPage =
        this.curriculumService.findAllByCriteria(
            keyword, totalDurationSeconds, topicIds, applyTopicFilter, pageable);

    List<SearchCurriculumResponse> curriculumResponses =
        this.buildSearchCurriculumResponses(curriculumSearchDTOPage.toList());

    return BaseResponse.build(
        PaginationResponse.<SearchCurriculumResponse>builder()
            .data(curriculumResponses)
            .currentPage(currentPage)
            .totalPages(curriculumSearchDTOPage.getTotalPages())
            .totalElements(curriculumSearchDTOPage.getNumberOfElements())
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

  private List<CurriculumResponse> buildCurriculumResponses(List<CurriculumDTO> curriculumDTOS) {
    return this.addLecturerIntoCurriculum(curriculumDTOS).stream()
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
                    .requirement(curriculumDTO.getRequirement())
                    .thumbnail(curriculumDTO.getThumbnail())
                    .topicId(curriculumDTO.getTopicId())
                    .topicName(curriculumDTO.getTopicName())
                    .build())
        .toList();
  }

  private List<SearchCurriculumResponse> buildSearchCurriculumResponses(
      List<CurriculumSearchDTO> curriculumSearchDTOS) {
    List<SearchCurriculumResponse> curriculumResponses =
        curriculumSearchDTOS.stream()
            .map(
                curriculumSearchDTO ->
                    SearchCurriculumResponse.builder()
                        .userId(curriculumSearchDTO.getUserId())
                        .id(curriculumSearchDTO.getId())
                        .title(curriculumSearchDTO.getTitle())
                        .headLine(curriculumSearchDTO.getHeadLine())
                        .cost(curriculumSearchDTO.getCost())
                        .description(curriculumSearchDTO.getDescription())
                        .requirement(curriculumSearchDTO.getRequirement())
                        .thumbnail(curriculumSearchDTO.getThumbnail())
                        .build())
            .toList();
    return this.addLecturerIntoCurriculumResponses(curriculumResponses);
  }

  private List<SearchCurriculumResponse> addLecturerIntoCurriculumResponses(
      List<SearchCurriculumResponse> curriculumResponses) {
    for (SearchCurriculumResponse curriculumResponse : curriculumResponses) {
      Long userId = curriculumResponse.getUserId();
      if (Objects.isNull(userId)) continue;

      String instructorKey = String.format(InstructorEnum.INSTRUCTOR_KEY.getContent(), userId);
      boolean isAvailableInstructorData = this.cacheService.hasKey(instructorKey);

      if (!isAvailableInstructorData) {
        BaseMessage<LoadLecturerIntoCachePayload> loadLecturerIntoCachePayloadMessage =
            BaseMessage.<LoadLecturerIntoCachePayload>builder()
                .type(MessageType.READ_LECTURER_INFO_AND_CACHE)
                .source(SourceMessageEnum.CURRICULUM_SERVICE)
                .createdAt(Instant.now())
                .payload(LoadLecturerIntoCachePayload.builder().userId(userId).build())
                .build();
        this.producerLoadLecturesIntoCacheService.send(loadLecturerIntoCachePayloadMessage);

        Optional<InstructorDTO> instructorDTOOptional = this.instructorService.findByUserId(userId);
        instructorDTOOptional.ifPresent(curriculumResponse::addLectureInfo);
        continue;
      }

      InstructorDTO lecturerDTO = this.cacheService.retrieveInstructorDTOAndRenewTTL(instructorKey);
      curriculumResponse.addLectureInfo(lecturerDTO);
    }
    return curriculumResponses;
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
                      .name(session.getName())
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

  private List<SessionDTO> mapSessionsToSessionDTO(List<Session> sessions) {
    return sessions.stream()
        .sorted(Comparator.comparing(Session::getIndex))
        .map(
            session -> {
              List<BaseSessionContentDTO> lectures = this.mapSessionContents(session);
              long totalDurationSeconds =
                  lectures.stream()
                      .filter(BaseSessionContentDTO::isVideo)
                      .map(VideoDTO.class::cast)
                      .mapToLong(VideoDTO::getDurationSeconds)
                      .sum();

              return SessionDTO.builder()
                  .id(session.getId())
                  .index(session.getIndex())
                  .name(session.getName())
                  .totalTimesStringFormat(
                      DurationConverterUtil.toStringDuration(
                          Duration.ofSeconds(totalDurationSeconds)))
                  .totalLectures(lectures.size())
                  .lectures(lectures)
                  .build();
            })
        .toList();
  }

  private List<BaseSessionContentDTO> mapSessionContents(Session session) {
    Map<Long, BaseSessionContentDTO> contentMap = new LinkedHashMap<>();

    for (Video video : session.getVideos()) {
      contentMap.put(
          video.getId(),
          new VideoDTO(
              video.getId(),
              video.getName(),
              video.getIsPreview(),
              video.getIndex(),
              session.getId(),
              video.getDurationSeconds()));
    }

    for (Exam exam : session.getExams()) {
      Long examContentKey = -exam.getId();
      contentMap.put(
          examContentKey,
          new ExamDTO(
              exam.getId(), exam.getName(), exam.getIsPreview(), exam.getIndex(), session.getId()));
    }

    return contentMap.values().stream().sorted().toList();
  }
}
