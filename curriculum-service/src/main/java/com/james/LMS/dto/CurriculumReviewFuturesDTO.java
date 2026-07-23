package com.james.LMS.dto;

import com.james.LMS.entity.Session;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class CurriculumReviewFuturesDTO {
  private CompletableFuture<List<Session>> sessionsFuture;
  private CompletableFuture<List<TopicDTO>> topicDTOSFuture;
  private CompletableFuture<Boolean> isExistWishListFuture;
  private CompletableFuture<Map<Long, List<BaseSessionContentDTO>>> collectContentSessionMapFuture;
  private CompletableFuture<InstructorDTO> instructorDTOFuture;
}
