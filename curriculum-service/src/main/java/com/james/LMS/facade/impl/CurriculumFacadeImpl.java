package com.james.LMS.facade.impl;

import com.james.LMS.dto.*;
import com.james.LMS.entity.*;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.facade.CurriculumFacade;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.CurriculumHomeResponse;
import com.james.LMS.response.CurriculumReviewResponse;
import com.james.LMS.service.*;
import com.james.LMS.util.DurationConverterUtil;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

  private static final Integer ZERO_LECTURE = 0;

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
  public BaseResponse<CurriculumHomeResponse> findCurriculumForHome() {
    return null;
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
