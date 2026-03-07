package com.james.LMS.service.impl;

import com.james.LMS.dto.CurriculumChannelDTO;
import com.james.LMS.dto.CurriculumDTO;
import com.james.LMS.dto.PurchasedCurriculumDTO;
import com.james.LMS.dto.WishListCurriculumDTO;
import com.james.LMS.entity.Curriculum;
import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.service.CurriculumService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
      List<Long> followedTopicIds, Pageable pageable) {
    return this.curriculumRepository.findAllCurriculumsByFollowedTopicIdsOfUser(
        followedTopicIds, pageable);
  }

  @Override
  public Page<CurriculumDTO> findAllCurriculumByTopicId(Long topicId, Pageable pageable) {
    return this.curriculumRepository.findAllCurriculumByTopicId(topicId, pageable);
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
}
