package com.james.LMS.service;

import com.james.LMS.dto.*;
import com.james.LMS.entity.Curriculum;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurriculumService {
  Optional<Curriculum> findById(Long id);

  List<CurriculumDTO> findAllInTopicOfUser(
      List<Long> topicIdsOfUser, Integer currentPage, Integer limit);

  Page<CurriculumDTO> findAllCurriculumsByFollowedTopicIdsOfUser(
      List<Long> followedTopicIds, Long userId, Pageable pageable);

  Page<CurriculumDTO> findAllCurriculumByTopicId(Long topicId, Long userId, Pageable pageable);

  Boolean existsCurriculumById(Long id);

  Boolean existsByIdAndChannelUserIdAndIsActiveIsTrue(Long curriculumId, Long userId);

  Page<PurchasedCurriculumDTO> findAllPurchasedCurriculums(Long userId, Pageable pageable);

  Optional<Curriculum> findByIdAndFetchChannel(Long id);

  Boolean isExistsById(Long id);

  Page<CurriculumDTO> findAllWishlist(Long userId, Pageable pageable);

  Page<WishListCurriculumDTO> findAllWishlistCurriculum(Long userId, Pageable pageable);

  Page<CurriculumChannelDTO> findAllInChannel(Long channelId, Pageable pageable);

  Page<CurriculumSearchDTO> findAllByCriteria(
      String keyword,
      Long totalDurationSeconds,
      Set<Long> topicIds,
      boolean applyTopicFilter,
      Pageable pageable);

  PaginationCurriculumMapDTO  findCurriculumPaginationByFollowedTopicIds(Integer currentPage, Integer pageSize, List<Long> followedTopicIds);

  Long saveAndFetchId(Curriculum curriculum);
}
