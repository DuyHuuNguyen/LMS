package com.james.LMS.service.impl;

import com.james.LMS.dto.*;
import com.james.LMS.entity.Curriculum;
import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.service.CurriculumService;

import java.util.*;

import com.james.LMS.util.SecurityUserDetailsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumServiceImpl implements CurriculumService {
  private final CurriculumRepository curriculumRepository;

  @Override
  public Optional<Curriculum> findById(Long id) {
    return this.curriculumRepository.findById(id);
  }

  @Override
  public List<CurriculumDTO> findAllInTopicOfUser(
      List<Long> topicIdsOfUser, Integer currentPage, Integer limit) {
    return List.of();
  }

  @Override
  public Page<CurriculumDTO> findAllCurriculumsByFollowedTopicIdsOfUser(
      List<Long> followedTopicIds, Long userId, Pageable pageable) {
    return this.curriculumRepository.findAllCurriculumsByFollowedTopicIdsOfUser(
        followedTopicIds, userId, pageable);
  }

  @Override
  public Page<CurriculumDTO> findAllCurriculumByTopicId(
      Long topicId, Long userId, Pageable pageable) {
    return this.curriculumRepository.findAllCurriculumByTopicId(topicId, userId, pageable);
  }

  @Override
  public Boolean existsCurriculumById(Long id) {
    return this.curriculumRepository.existsCurriculumByIdAndIsActiveIsTrue(id);
  }

  @Override
  public Boolean existsByIdAndChannelUserIdAndIsActiveIsTrue(Long curriculumId, Long userId) {
    return this.curriculumRepository.existsByIdAndChannel_UserIdAndIsActiveIsTrue(
        curriculumId, userId);
  }

  @Override
  public Page<PurchasedCurriculumDTO> findAllPurchasedCurriculums(Long userId, Pageable pageable) {
    return this.curriculumRepository.findAllPurchasedCurriculums(userId, pageable);
  }

  @Override
  public Optional<Curriculum> findByIdAndFetchChannel(Long id) {
    return this.curriculumRepository.findByIdFetchChannel(id);
  }

  @Override
  public Boolean isExistsById(Long id) {
    return this.curriculumRepository.existsById(id);
  }

  @Override
  public Page<CurriculumDTO> findAllWishlist(Long userId, Pageable pageable) {
    return this.curriculumRepository.findAllWishlist(userId, pageable);
  }

  @Override
  public Page<WishListCurriculumDTO> findAllWishlistCurriculum(Long userId, Pageable pageable) {
    return this.curriculumRepository.findAllWishlistCurriculum(userId, pageable);
  }

  @Override
  public Page<CurriculumChannelDTO> findAllInChannel(Long channelId, Pageable pageable) {
    return this.curriculumRepository.findAllInChannel(channelId, pageable);
  }

  @Override
  public Page<CurriculumSearchDTO> findAllByCriteria(
      String keyword,
      Long totalDurationSeconds,
      Set<Long> topicIds,
      boolean applyTopicFilter,
      Pageable pageable) {
    return this.curriculumRepository.findAllByCriteria(
        keyword, totalDurationSeconds, topicIds, applyTopicFilter, pageable);
  }


  public PaginationCurriculumMapDTO findCurriculumPaginationByFollowedTopicIds(Integer currentPage,Integer pageSize,List<Long> followedTopicIds){
    Map<String, PaginationDTO<CurriculumDTO>> topicNameCurriculumDTOMap = new HashMap<>();
    List<List<CurriculumDTO>> curriculumDTOSList = new ArrayList<>();

    Pageable pageable = PageRequest.of(currentPage, pageSize);

    for (var topicId : followedTopicIds) {
      Page<CurriculumDTO> curriculumDTOPage =
              this.findAllCurriculumByTopicId(topicId, SecurityUserDetailsUtil.PRINCIPAL.getId(), pageable);

      boolean isNotFoundCurriculum = curriculumDTOPage.isEmpty();
      if (isNotFoundCurriculum) continue;

      List<CurriculumDTO> curriculumDTOS =curriculumDTOPage.toList();
      curriculumDTOSList.add(curriculumDTOS);

      String topicName = curriculumDTOS.getFirst().getTopicName();
      PaginationDTO<CurriculumDTO> curriculumDTOPaginationDTO =
              PaginationDTO.<CurriculumDTO>builder()
                      .totalItems(curriculumDTOS.size())
                      .currentPage(currentPage)
                      .pageSize(pageSize)
                      .data(curriculumDTOS)
                      .build();

      topicNameCurriculumDTOMap.put(topicName, curriculumDTOPaginationDTO);
    }

    return PaginationCurriculumMapDTO.builder().curriculumDTOSList(curriculumDTOSList).topicNameCurriculumDTOMap(topicNameCurriculumDTOMap).build();
  }

  @Override
  public Long saveAndFetchId(Curriculum curriculum) {
    return this.curriculumRepository.save(curriculum).getId();
  }
}
